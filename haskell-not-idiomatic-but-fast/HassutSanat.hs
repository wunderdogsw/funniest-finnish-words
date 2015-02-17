{-# LANGUAGE BangPatterns, DataKinds, MagicHash, UnboxedTuples #-}
import qualified Data.ByteString as B
import qualified Data.ByteString.Char8 as BC
import Data.Char (isLetter)
import Data.List (sortBy)
import Data.Ord (comparing)
import qualified Data.Text as T
import qualified Data.Text.Encoding as TE
import qualified Data.Text.Encoding.Error as TEE
import qualified Data.Text.Internal.Fusion as S
import GHC.Exts
import GHC.Prim ()

isVowel :: Char -> Bool
isVowel 'A' = True
isVowel 'E' = True
isVowel 'I' = True
isVowel 'O' = True
isVowel 'U' = True
isVowel 'Y' = True
isVowel 'Å' = True
isVowel 'Ä' = True
isVowel 'Ö' = True
isVowel 'a' = True
isVowel 'e' = True
isVowel 'i' = True
isVowel 'o' = True
isVowel 'u' = True
isVowel 'y' = True
isVowel 'å' = True
isVowel 'ä' = True
isVowel 'ö' = True
isVowel _   = False

score :: Int# -> Int#
score 0# = 0#
score 1# = 2#
score 2# = 8#
score 3# = 24#
score 4# = 64#
score i = i *# double2Int# ((int2Double# 2#) **## (int2Double# i))

foldl' :: ((# Int#, Int# #) -> Char -> (# Int#, Int# #)) -> (# Int#, Int# #) -> S.Stream Char -> (# Int#, Int# #)
foldl' !f !z0 !(S.Stream !next !s0 !_len) = loop_foldl' z0 s0
    where
      loop_foldl' !z !s = case next s of
                            !S.Done           -> z
                            !(S.Skip !s')     -> loop_foldl' z s'
                            !(S.Yield !x !s') -> loop_foldl' (f z x) s'

ifoldl :: ((# Int#, Int# #) -> Char -> (# Int#, Int# #)) -> (# Int#, Int# #) -> T.Text -> (# Int#, Int# #)
ifoldl !f !i !t = foldl' f i (S.stream t)

funniness :: T.Text -> Int
funniness !word = let !(# !t, !s #) = ifoldl scoreCalculator (# 0#, 0# #) word
                  in  I# (t +# (score s))
                  where scoreCalculator :: (# Int#, Int# #) -> Char -> (# Int#, Int# #)
                        !scoreCalculator = (\ !(# !total, !current #) !c -> if isVowel c then (# total, current +# 1# #) else (# total +# (score current), 0# #))

comparingDesc :: Ord a => (b -> a) -> b -> b -> Ordering
comparingDesc f x y = comparing f y x

main :: IO ()
main = do
  rawContent <- B.getContents
  let !utf8Content = TE.decodeUtf8With TEE.strictDecode rawContent
  let !individualWords = T.words $ utf8Content
  let !wordsWithFunniness = map (\w -> (w, funniness w )) $ individualWords
  let !wordsSortedByFunniness = sortBy (comparingDesc snd) wordsWithFunniness
  let !funninessOfFunniestWord = snd $ head wordsSortedByFunniness
  let !funniestWords = takeWhile (\w -> snd w == funninessOfFunniestWord) wordsSortedByFunniness
  mapM_ BC.putStrLn . map TE.encodeUtf8 . map (T.filter isLetter) . map fst $ funniestWords


import Data.Char (isLetter, toLower)
import Data.List (groupBy, sortBy)

isVowel :: Char -> Bool
isVowel = flip elem ['a', 'e', 'i', 'o', 'u', 'y', 'å', 'ä', 'ö'] . toLower

vowelGroups :: String -> [String]
vowelGroups ""                   = []
vowelGroups s@(c:cs) | isVowel c = fst sp : vowelGroups (snd sp)
                                   where sp = span isVowel s
vowelGroups s                    = vowelGroups $ dropWhile (not . isVowel) s

vowelGroupSizes :: String -> [Int]
vowelGroupSizes = map length . vowelGroups

vowelGroupScore :: Int -> Int
vowelGroupScore i = i * (2 ^ i)

funniness :: String -> Int
funniness = sum . map vowelGroupScore . vowelGroupSizes

compareByFunninessDesc :: String -> String -> Ordering
compareByFunninessDesc a b = compare (funniness b) (funniness a)

main = do
  getContents >>= mapM_ putStrLn . map (filter isLetter) . head . groupBy (\a b -> funniness a == funniness b) . sortBy compareByFunninessDesc . words

# -*- coding: utf-8 -*-

"""
Find the funniest Finnish word in a given text based on funniness rules at:
http://wunderdog.fi/koodaus-hassuimmat-sanat/ (only in Finnish, sorry).

Features and deficiencies:
- Considers hyphenated words as one word, e.g. pula-aika.
- Words that start a quote (with single or double quotes) have the starting
  quote character as their first character. Note that single quotes
  (apostrophes) are normal inside a Finnish word, e.g. liu'uttaa.
"""

from __future__ import print_function

import re
import sys

VOWEL_SERIES_RE = re.compile(u'[aeiouyåäöAEIOUYÅÄÖ]+')
STRIP_CHARS = '.,"\'?!:;'

def get_input_text(filename):
    with open(filename) as f:
        text = f.read()
    return text

def split_to_words(text):
    words = text.split()
    return words

def _score_vowels(vowel_series):
    # Give score for a vowel_series, e.g. [1, 2, 2, 1].
    def __score(n):
        return n * (2 ** n)
    return reduce(lambda x, y: x + y, map(__score, vowel_series), 0)

def _find_vowel_series(word):
    # Find series of vowels for a word, e.g. 'aamiainen' => [2, 3, 1].
    return [len(vs) for vs in VOWEL_SERIES_RE.findall(word)]

def _score(word):
    vowel_series = _find_vowel_series(word)
    return _score_vowels(vowel_series)

def find_funniest_word(words):
    """Find word(s) with the biggest funniness score."""
    max_score = 0
    funniest_words = []

    for word in words:
        word_score = _score(word)

        if word_score > max_score:
            funniest_words = []
            funniest_words.append(word)
            max_score = word_score
        elif word_score == max_score:
            funniest_words.append(word)

    return funniest_words

def main(filename):
    text = get_input_text(filename)
    words = split_to_words(text)
    funniest = find_funniest_word(words)
    for word in funniest:
        print(word.strip(STRIP_CHARS))

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('Usage: %s <filename>' % (sys.argv[0]), file=sys.stderr)
        sys.exit(1)

    main(sys.argv[1])

#include <ctype.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

const int BUFFER_SIZE = 1024;
const int MAXIMUM_WORD_SIZE = 128;
const int MAXIMUM_VOWEL_GROUPS_LENGTH_IN_ONE_WORD = 32;
const int MAXIMUM_FUNNIEST_WORDS = 32;

const int FINNISH_UMLAUT_VOWELS_FIRST_BYTE_AS_INT = -109;

static inline bool is_vowel(char ch) {
    switch (ch) {
        case 'A':
        case 'E':
        case 'I':
        case 'O':
        case 'U':
        case 'Y':
        case 'a':
        case 'e':
        case 'i':
        case 'o':
        case 'u':
        case 'y':
            return true;
        default:
            return false;
    }
}

/**
 * The first byte of Å, Ä, Ö, å, ä and ö is c3 (-109).
 */
static inline bool is_finnish_umlaut_vowel_second_byte(int byte) {
    switch (byte) {
        case -171: // Å = c385
        case -172: // Ä = c384
        case -154: // Ö = c396
        case -139: // å = c3a5
        case -140: // ä = c3a4
        case -122: // ö = c3b6
            return true;
        default:
            return false;
    }
}

static inline int score(int vowel_group_length) {
    return vowel_group_length == 0 ? 0 : vowel_group_length * pow(2, vowel_group_length);
}

int main() {
    char buffer[BUFFER_SIZE];
    char current_word[MAXIMUM_WORD_SIZE];
    int current_word_idx = 0;
    int current_word_vowel_group_lengths[MAXIMUM_VOWEL_GROUPS_LENGTH_IN_ONE_WORD];
    int current_word_vowel_group_lengths_idx = 0;
    int max_score = 0;
    char winning_words[MAXIMUM_FUNNIEST_WORDS][MAXIMUM_WORD_SIZE];
    int winning_words_idx = 0;

    memset(buffer, '\0', sizeof(buffer));
    memset(current_word, '\0', sizeof(current_word));

    char current_char = 0;
    int current_int = 0;
    char previous_char = 0;
    int previous_int = 0;

    char *fgets_result;

    do {
        fgets_result = fgets(buffer, BUFFER_SIZE, stdin);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            previous_char = current_char;
            previous_int = previous_char - '0';
            current_char = buffer[i];
            current_int = current_char - '0';

            if (current_int == FINNISH_UMLAUT_VOWELS_FIRST_BYTE_AS_INT) {
                current_word[current_word_idx++] = current_char;
                continue;
            }

            if (!isalpha(current_char) && !is_finnish_umlaut_vowel_second_byte(current_int) && current_int != FINNISH_UMLAUT_VOWELS_FIRST_BYTE_AS_INT) {
                // A new word is about to begin
                // Was the previous word a new winner?
                if (current_word_idx != 0) {
                    int previous_word_score = 0;
                    for (int vowel_group_idx = 0; vowel_group_idx < MAXIMUM_VOWEL_GROUPS_LENGTH_IN_ONE_WORD; vowel_group_idx++) {
                        previous_word_score += score(current_word_vowel_group_lengths[vowel_group_idx]);
                    }
                    if (previous_word_score > max_score) {
                        max_score = previous_word_score;
                        memcpy(winning_words[0], current_word, MAXIMUM_WORD_SIZE);
                        winning_words_idx = 1;
                    } else if (previous_word_score == max_score) {
                        memcpy(winning_words[winning_words_idx++], current_word, MAXIMUM_WORD_SIZE);
                    }
                    // Skip spaces till we find a word again
                    // Zero the counters
                    current_word_idx = 0;
                    current_word_vowel_group_lengths_idx = 0;
                    current_word_vowel_group_lengths[current_word_vowel_group_lengths_idx] = 0;

                    memset(current_word, '\0', sizeof(current_word));
                    memset(current_word_vowel_group_lengths, 0, sizeof(current_word_vowel_group_lengths));
                }
            } else {
                current_word[current_word_idx++] = current_char;
                if (is_vowel(current_char) && current_int != FINNISH_UMLAUT_VOWELS_FIRST_BYTE_AS_INT) {
                    current_word_vowel_group_lengths[current_word_vowel_group_lengths_idx] = current_word_vowel_group_lengths[current_word_vowel_group_lengths_idx] + 1;
                } else {
                    current_word_vowel_group_lengths_idx = current_word_vowel_group_lengths_idx + 1;
                    current_word_vowel_group_lengths[current_word_vowel_group_lengths_idx] = 0;
                }
            }
        }
        memset(buffer, '\0', sizeof(buffer));
    } while (fgets_result != NULL);

    for (int i = 0; i < winning_words_idx; i++) {
        printf("%s\n", winning_words[i]);
    }
}

#!/usr/bin/perl -W
# author: Heikki Salokanto, Wunderdog coding puzzle #1, 2015
use strict;
use utf8;
use open qw(:std :utf8);

my $highest = 0;
my %words = ();

while (my $line = <>) {
    foreach (split /\W+/, $line) {
        next if $words{$_};

        my @groups = /[aeiouyåäö]+/gi;
        my $score = 0;
        map {$score += $_ * (2 ** $_)} map {length $_} @groups;

        $words{$_} = $score;
        $highest = $score if $score > $highest;
    }
}

my @winners = grep {$words{$_} == $highest} keys %words;
print "Score $highest: " . join(', ', @winners);

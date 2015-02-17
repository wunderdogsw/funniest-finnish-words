# author: Jari Hennilä, 2015
require 'net/http'
puts Net::HTTP.get('wunderdog.fi', '/koodaus-hassuimmat-sanat/alastalon_salissa.txt').force_encoding('utf-8').downcase.split.uniq.collect { |s| { word: s, seqs: s.scan(/[aeiouyäöå]+/) } }.collect { |h| { word: h[:word], score: h[:seqs].inject(0) { |a, e| a += e.length * 2 ** e.length } } }.sort { |lhs, rhs| rhs[:score] <=> lhs[:score] }.reduce(nil) { |r, w| r.nil? ? [w] : r.first[:score] == w[:score] ? r + [w] : r }

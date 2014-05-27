##
# Author: @anandh_somu
# Date: 28-May-2014
# Learn a new Language
# Ref: http://www.reddit.com/r/dailyprogrammer/comments/26ijiu/5262014_challenge_164_easy_assemble_this_scheme/

class TryingRuby
	def printHello()
		p "Hello world!"	
	end

	def divisors()
	#First way
		(1..100).each do |num|
			if num%15 == 0
				print num,' |'
			end
		end
	#Second way
		100.times do |count|
			if count%15 == 0
				print count,' '
			end
		end	
	end	

	def anagram(newWord, baseWord)
	 	if baseWord.downcase.chars.sort.join == newWord.downcase.chars.sort.join
	 		print "\nIt is an anagram\n"
	 		return
		end
		print "\nNot an anagram\n"
	end

	def removeLetter(word, letter)
		p word.delete!(letter)
	end

	def sumArray(nums = Array[1, 2, 3, 50, 100])
		sum = 0
		nums.each do |val|
			sum+= val
		end
		p sum
	end

	def bubbleSort(nums = Array[100, -2, 33, 8, 80])
		sorted = false

		until sorted
			sorted = true
			for index in 0..(nums.length-2)
				if nums[index] > nums[index+1]
					sorted = false
					nums[index], nums[index+1] = nums[index+1], nums[index]
				end
			end
		end
		print nums
	end
end

rubyTest = TryingRuby.new

rubyTest.printHello()
rubyTest.divisors
rubyTest.anagram('Anagram','GranaAm')
rubyTest.removeLetter('removeP','P')
rubyTest.sumArray(Array[1, 20, 3, 40, 5])
rubyTest.bubbleSort
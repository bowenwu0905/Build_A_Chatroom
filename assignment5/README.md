# Assignment5_concurrency_project
CS5010 repo for Spring2022; Northeastern Seattle.

## Introduction

The Open University Learning Analytics Dataset (OULAD) contains real data collected from
students in online courses at the Open University in the UK. 

The dataset includes information
about each student’s performance in the course and their interactions with online
materials.

The project helps automate analysis of the OULAD files. 

The format of the data in courses.csv is:
code_module, code_presentation, code_presentation_length

The format of the data in studentVle is:
code_module, code_presentation, id_student, id_site, date, sum_click

Part1 and Part2 of the project write a program that will read in these files and use them
to create new summary files so that each individual code_presentation of a code_module has a file
listing the total number of clicks that occurred on a particular day. Each new file should be
named code_presentation_code_module.csv and should contain two columns: date, and
total_clicks.
For example, one code_module is “AAA”. It has two code_presentations, one of which is “2013J”
and the other is “2014J”. Therefore, the program should produce two csv files for this module:
AAA_2013J.csv and AAA_2014J.csv. Within each file, each row should contain one date and the
total number of clicks that occurred in the presentation across all id_sites and all id_students. For
example, one row in “AAA_2013J.csv” should contain -10, 11952, meaning that there were 11952
clicks in this course presentation on day -10.


## part1_Sequential_solution

How to run: The sequential solution accept the directory containing the OULAD csv files as a command line 
argument. It consists of three classes in total.

* The Main class takes in args, and generate file in destinationPath.

* The CSVProcesser class takes in inputArgs as fields. The class is used to process the course.csv and studentVle.csv file and generated a hashmap<String, Map<String, Integer>>. 

* The CSVGenerator class is used to generate the csv file with the information contained in hashmap.

* You can see the final output in output_part1

## part2_Concurrent_solution

How to run: The concurrent solution accept the directory containing the OULAD csv files as a command line
argument. In default, it will be "data" to put in the arguments when you run "run" function in Main

  * Main. It will maintain a blocking queue and passing between
  consumers and a producer. All data is maintained in CourrentHasmap. The concurrency is controlled 
  through locks

  * CSVprocessor: processing the CSV
  * FilePublisher: It is for generating the text file based on the hashmap. Also, it
  generating the files' names and lookup table for lock
  * The producer class. It is for read in the raw csv data line by line. And it will push the data
  into a fixed sized queue. If the queue is full, the buffer will stop temporarily. When the whole
  csv file is read in, the producer will stop and the latch will count down. So the consumer can
  know the producer is finished
  * The consumer class. It is for updtaing the hashmap. It will keep running until the producer
  terminates and the buffer is empty



You will see the ouput in output_part2





## part3_indentify_high_activity_days
How to run: The part3 using the output of part2, which is the days' activity record of courses.
The program needs three arguments as input, the first is the path of course.csv, which
is default set as "data". The second set is the output of part2, which is "output_part2".
And, the third one is threshold, for example, 5000.

* Main. It will maintain two blocking queue and passing between consumers and producers. one is store 
all data, and another one is store data which meets the threshold.
* CSVprocessor: processing the CSV
*FilePublisher: It is for generating the text file based on the hashmap. Also, it generating the files' names
* The producer class. It is for read all files in the output folder of part2, and put into blockingQueue
* The consumer class. It is for updtaing the blockingQueue. It will keep running until the producer terminates and the buffer is empty
 
You will see the ouput in output_part3



## Note
 - The directory containing the OULAD csv files are "data". "data" will be the input for part 1 and part 2 problem
 - The output folder are named in the fashion in "output_part#"
 - For UMl, the files end with "_complete" are the final version in UML folder

# Assignment4_concurrency_project
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

part1 and part2 of the project write a program that will read in these files and use them
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

The sequential solution accept the directory containing the OULAD csv files as a command line 
argument. 




## part2_Concurrent_solution

## part3_indentify_high_activity_days

# Overlook
1. [Design of implementation](README.md#Design of implementation)
2. [Description of each class](README.md#Description of each class)
3. [Results](README.md#Results)
4. [Conclusion](README.md#Conclusion)

# Design of implementation
For this challenge, we have two missions to do:

1. Read the input file and write the output file.

2. Processing data, which require us to find the data we need, and do some calculates to get the right format of output.

So I design the two classes `FileProcessor` and `MedianFinder` to finish the mission repsectively. Another class `Main` is just the entry of program, and it takes the arguments which indicate the input and output files path.

# Description of each class
Here are three classes in total, each class has it's own methods to finish the job.

## Main
The entry of the whole program, takes arguments and pass them to methods of other class.

## FileProcessor
Class for read and write files, use maps to store data. Since we need find the median of contributions, total number of transactions and total amount of contributions, the best way to connect these data to CMTE_ID/ZIP_CODE/TRANSACTION_DT is to use Map. For the first output file, which need to output in stream way, we can simply use HashMap. The `medianvals_by_zip.txt` file should be written while reading the input file line by line. For the second output file, whcih cannot be written until we have collected all of the input data. Because this file should be sorted alphabetical by recipient and then chronologically by date, the good way is to use TreeMap during the storing process. After reading all of the input data, we can calculates the median and write the file out.

## MedianFinder
When we want to get the median of a set of data, we have to sort this data set, and this takes a lot of time when write the first output file since we must sort data set everytime we reading a line from inputfile. So I use two PriorityQueues as the data set: the first stores the smaller half of data set while the second store the bigger half. When we want to find the median we can simply pop the first element of the longer heap or get the mean of two elements of each heap when the size is same. In this way, we could speed up the process a lot.

# Results
The program shows a good result, it runs about 3.7 second to process a 45.6MB text file, and about 31.2 second to process a 828.8MB text file with good accuracy. This result seems to be fine without using distributed technologies, databases, nor multi-threading.

# Conclusion
Overall I think it's a good solution for this problem, and it's reasonable to deal with somehow "large data" since the modern computer has strong cpu and enough memory and disk. But if we really need to process larger data or a lot of "not viry big data" files, we would better consider to use multi-threading, databases or distributed technologies. These technologies can make the problem easier to solve with less code and less time.
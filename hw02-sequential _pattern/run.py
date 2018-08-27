import os

os.system("javac *.java")
dataset1 = "./Dataset/C50S10T2.5N10000.ascii"
dataset2 = "./Dataset/C50S10T5N10000.ascii"

min_sup = 0.1
for i in range(6):
    print("min_sup: " + str(min_sup))
    os.system("java Client {0} {1}".format(dataset1 ,min_sup))
    min_sup = min_sup / 2

min_sup = 0.1
for i in range(6):
    print("min_sup: " + str(min_sup))
    os.system("java Client {0} {1}".format(dataset2 ,min_sup))
    min_sup = min_sup / 2
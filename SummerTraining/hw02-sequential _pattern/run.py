import os

os.system("javac *.java")
dataset1 = "./Dataset/C50S10T2.5N10000.ascii"
dataset2 = "./Dataset/C50S10T5N10000.ascii"

min_sup = [0.05, 0.04, 0.03, 0.02, 0.01] 
min_sup2 = [0.08, 0.07, 0.06, 0.05, 0.04]
for i in min_sup:
	print("min_sup: " + str(i))
	os.system("java Client {0} {1}".format(dataset1 ,i))

for i in min_sup2:
	print("min_sup: " + str(i))
	os.system("java Client {0} {1}".format(dataset2 ,i))

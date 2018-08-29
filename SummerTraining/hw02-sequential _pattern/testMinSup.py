import os

os.system("javac test.java")
min_sup = 1.0
for i in range(15):
    print("min_sup: " + str(min_sup))
    os.system("java test {0}".format(min_sup))
    min_sup = min_sup / 2
    

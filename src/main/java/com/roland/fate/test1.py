import platform
import sys
import time

def run_sql(sql_statement):
    print("run sql: {}".format(sql_statement))

    print("return:","aaa")
    time.sleep(5)
    with open("/Users/roland/IntellJProject/mycommunity/src/main/java/com/roland/fate/test.pp",'w') as f:
        f.write("hahaha")

    return "job finish"

if __name__ == '__main__':
    print(sys.version)
    for i in range(0,len(sys.argv)):
        print(sys.argv[i])

        run_sql(sys.argv[i])

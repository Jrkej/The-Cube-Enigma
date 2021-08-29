import json
import os
import random

directory = os.path.dirname(__file__)
color = ["w", "r", "y", "o", "b", "g"]
tests = 50
validators = 30
names = [
    "Tutorial",
    "Let the Code begin",
    "fascinated",
    "unravelling",
    "Lost!",
    "Trauma",
    "Child's special",
    "enigmatic problem",
    "puzzled",
    "baffled",
    "colourfull",
    "6 coloured rainbow",
    "Fix your code",
    "Greed",
    "Search",
    "Burst",
    "code more",
    "cubeigame",
    "corners",
    "plaque",
    "locust",
]

def middles(b):
    l = []
    for i in range(6):
        if b[i][1][1] == 0:
            l.append([i, 1, 1])
    return l

def edges(b):
    l = []
    for i in range(6):
        if b[i][1][0] == 0:
            l.append([i, 1, 0])
        if b[i][1][2] == 0:
            l.append([i, 1, 2])
        if b[i][0][1] == 0:
            l.append([i, 0, 1])
        if b[i][2][1] == 0:
            l.append([i, 2, 1])
    return l

def corners(b):
    l = []
    for i in range(6):
        if b[i][0][0] == 0:
            l.append([i, 0, 0])
        if b[i][0][2] == 0:
            l.append([i, 0, 2])
        if b[i][2][0] == 0:
            l.append([i, 2, 0])
        if b[i][2][2] == 0:
            l.append([i, 2, 2])
    return l

def generator():
    b = [[[0, 0, 0]for i in range(3)]for k in range(6)]
    for i in range(6):
        m = middles(b)
        e = edges(b)
        c = corners(b)
        r = random.choice(m)
        b[r[0]][r[1]][r[2]] = color[i]
        for o in range(4):
            r1 = random.choice(e)
            r2 = random.choice(c)
            b[r1[0]][r1[1]][r1[2]] = color[i]
            b[r2[0]][r2[1]][r2[2]] = color[i]
            e.remove(r1)
            c.remove(r2)
    return b


def getStr(b):
    s = ""
    for f in range(6):
        for x in range(3):
            for y in range(3):
                s += b[f][x][y]
    return s

data = [getStr(generator())for i in range(tests)]
print(data)
iniTest = 0
DefaultViewTest = {
	"title": {
		"2": "test",
		"1": "démarreur"
	},
	"testIn": "bogrwoyrgwgrbryyoywrbbyrrywwyobowobrrggobwygogyowgwbgb",
	"isTest": "true",
	"isValidator": "true"
}
for test in data:
    name = "test" + str(iniTest)
    DefaultViewTest["title"]["2"] = name if iniTest >= len(names) else names[iniTest]
    DefaultViewTest["title"]["1"] = name if iniTest >= len(names) else names[iniTest]
    DefaultViewTest["testIn"] = test
    if iniTest >= tests-validators:
        DefaultViewTest["isTest"] = "false"
    json_object = DefaultViewTest
    with open(directory + "\\" + name + ".json", "w") as js:
        json.dump(json_object, js)
        print(iniTest, name if iniTest >= len(names) else names[iniTest])
    iniTest += 1
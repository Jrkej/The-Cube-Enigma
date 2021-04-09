import json
import os

directory = os.path.dirname(__file__)
f = open(directory + '\\tests.json', "r")
data = json.load(f)
print(data)
iniTest = 8
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
    DefaultViewTest["title"]["2"] = name
    DefaultViewTest["title"]["1"] = name
    DefaultViewTest["testIn"] = test
    if iniTest >= 10:
        DefaultViewTest["isTest"] = "false"
    json_object = DefaultViewTest
    with open(directory + "\\" + name + ".json", "w") as js:
        json.dump(json_object, js)
        print(iniTest)
    iniTest += 1

f.close()
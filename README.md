# Myfaces Captcha Generator
Generates a bunch of captchas in a folder. Thats it!

## How the captcha looks like
![](https://github.com/danielgatis/myfaces-captcha-generator/raw/master/dist/7Aj7i8.jpg)

## Generating captchas
I'm a nice guy, so there is an already compiled version on dist folder.
Just run this command:
```bash
$ java -jar dist/generator.jar 1000 ./captchas
```

## Building
In the root directory run this command:
```bash
$ mvn clean compile assembly:single
```

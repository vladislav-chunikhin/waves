# Развёртывание проекта на хостинге с помощью сервиса heroku

## Первый шаг
- Установите [apache_maven](https://maven.apache.org/install.html) и [jdk_1.8](https://openjdk.java.net/install/)
- Установите [git](https://git-scm.com/downloads)
> создайте папку для проекта mkdir foldername >>
> перейдите в данную папку cd ~/foldername >> инициализация локального гит репозитория git init >>
> добавление удаленного репозитория git remote add https://github.com/Vladislav21/fortune.git >>
> убедитесь что вы добавили удаленный репозиторий командой git remote -v >> появится что-то типо того origin  git://github.com/schacon/ticgit.git >>
> заберите проект в созданную папку git pull >> по итогу у вас будет структура проекта с папками src/main, doc, resources, файлы настроек и папка maven
- Перед тем как начать развёртывание проекта с помощью сервиса heroku, нужно убедиться в том, 
что он собирается без ошибок с помощью сборщика maven: mvn clean install 
и проходит компиляцию данной командой: java -jar target/fortune-0.0.1-develop.jar
- По итогу конечным файлом с расширением .jar является fortune-0.0.1-develop.jar (name-version-status.jar)
, который появляется в папке target после выполнения команды mvn clean install

## Второй шаг
- Теперь необходимо удостоверится, что в файле pom.xml имеется данный плагин:
-            <plugin>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-maven-plugin</artifactId>
                 <executions>
                     <execution>
                         <goals>
                             <goal>build-info</goal>
                         </goals>
                         <configuration>
                             <additionalProperties>
                                 <encoding.source>${project.build.sourceEncoding}</encoding.source>
                                 <encoding.reporting>${project.reporting.outputEncoding}</encoding.reporting>
                                 <java.source>${maven.compiler.source}</java.source>
                                 <java.target>${maven.compiler.target}</java.target>
                             </additionalProperties>
                         </configuration>
                     </execution>
                 </executions>
             </plugin>
- Далее создаём файл Procfile.txt, если он отсутствует в root проекта. Это инструкция, которую мы пишем для сервиса heroku.
Файл содержит следующее: web: java -jar target/fortune-0.0.1-develop.jar

- [Официальная документация настройки репозитория heroku](https://devcenter.heroku.com/articles/git)
- Если в качестве удалённого репозитория используется github, то всё становится легче. [Интеграция с github](https://devcenter.heroku.com/articles/github-integration)
- Дальше всё делаем по шагам, которые описаны на heroku и всё должно работать. Good luck!

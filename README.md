# OnLite  [![](https://jitpack.io/v/mr-absurd/onlite.svg)](https://jitpack.io/#mr-absurd/onlite)
OnLite is a database framework for Android, you do not need to write any sql statement. Onlite to achieve the database data records and java objects between the mapping, increase, delete, check, change, you can achieve through the java object.
# How to
To get a Git project into your build:
## Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
## Step 2. Add the dependency

	dependencies {
          compile 'com.github.mr-absurd:onlite:v1.0.1'
          
	}
# Instruction
You do not need to write sql, you can directly operate javabean.
```Java
    
    //@OnTable("tablename") table name is class name if you don't annotate.
    public class User{
      //@OnAutoInscrement() 
      private Integer id;
      private String username;
      private String passwd;
    }
```

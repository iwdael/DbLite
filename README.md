# OnLite  [![](https://jitpack.io/v/aliletter/onlite.svg)](https://jitpack.io/#aliletter/onlite)
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
          compile 'com.github.aliletter:onlite:v1.1.5'
          
	}
# Instruction
## You do not need to write sql, you can directly operate javabean.
```Java
    
    //@OnTable("tablename") table name is class name if you don't annotate.
    public class User{
      //@OnAutoInscrement() 
      private Integer id;
      private String username;
      private String passwd;
    }
```
## Create a new class to inherit onlite, you do not need to implement any method inside
```Java
public class UserLite extends OnLite<User> {

}
```
## Get a reference to the data table
```Java
	UserLite userLite = OnLiteFactory.getInstance().getDataHelper(UserLite.class, User.class);
```
## Insert
```Java
	User user = new User();
        user.setPswd("admin");
        user.setUsername("admin");
	userLite.insert(user)
```
## Delete
```Java
	//delete all
	userLite.delete(null);
	//delete that username is 'admin'
	User where=new User();
	where.setUsername("admin");
	userLite.delete(where);
```
## Updata
```Java
	User user = new User();
        user.setPswd("admin");
        user.setUsername("1233");
	
	User where=new User();
	where.setUsername("admin");
	// Update the data for username is admin
	userLite.updata(user,where);
```
## Select
```Java
	//select all
	userLite.select(null);
	//select that username is 'admin'
	User where=new User();
	where.setUsername("admin");
	userLite.select(where);
```
## Delete the current reference table, and the reference will also be invalid
```Java
 	userLite.deleteTable();
```

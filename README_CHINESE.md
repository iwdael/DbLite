# OnLite  [![](https://jitpack.io/v/aliletter/onlite.svg)](https://jitpack.io/#aliletter/onlite)
OnLite是Android的数据库框架，你不需要写任何的sql语句。 Onlite实现数据库的数据记录和java对象之间的映射。增加，删除，查询，修改，都可以通过java对象来实现。
## 使用说明
在使用OnLite之前，必须先通过OnLiteFactory中的getInstance方法初始化Onlite。OnLite实现了一些比较复杂的查询，比如按照某个字段升序或者倒序分页查询等。
### 代码示例
初始化OnLiteFactory
```Java
    OnLiteFactory.getInstance("/sdcard/Music/");
```
JavaBean实体类
```Java
public class User {
    @OnAutoIncreament
    private Integer id;
    private String username;
    private String pswd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

}
```
UserLite
```Java
public class UserLite extends OnLite<User> {
}
```
获取表的引用
```Java
    UserLite userLite = OnLiteFactory.getInstance().getDataHelper(UserLite.class, User.class);
```
插入数据
```Java
	User user = new User();
    user.setPswd("admin");
    user.setUsername("admin");
	userLite.insert(user)
```
删除所有数据
```Java
    userLite.delete(null);
```
根据条件删除数据
```Java
	User where=new User();
	where.setUsername("admin");
	userLite.delete(where);
```
删除数据表
```Java
    userLite.deleteTable();
```
根据条件更新数据
```Java
    User user = new User();
    user.setPswd("admin");
    user.setUsername("1233");
    User where=new User();
    where.setUsername("admin");
    userLite.updata(user,where);
```
根据条件更新数据，如果找不到匹配的数据就插入该数据
```Java
    User user = new User();
    user.setPswd("admin");
    user.setUsername("1233");
    User where=new User();
    where.setUsername("admin");
    userLite.updataOrInsert(user,where);
```
查询表
```Java
    /**
     * @param where condition
     * @param conditions customized condition set
     * @param limit The number of data
     * @param page page number
     * @param orderColumnName Ascending or descending corresponding to the field or descending corresponding to the field
     * @param asc if true,ascending
     * @return list<User>
     */
    userLite.select(User where, List<Condition> conditions, Integer limit, Integer page, String orderColumnName, Boolean asc);
```
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

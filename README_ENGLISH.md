# OnLite  [![](https://jitpack.io/v/hacknife/onlite.svg)](https://jitpack.io/#hacknife/onlite)
OnLite is a database framework for Android, you do not need to write any sql statement. Onlite to achieve the database data records and java objects between the mapping, increase, delete, check, change, you can achieve through the java object.[中文文档](https://github.com/hacknife/OnLite/blob/master/README.md)
## Instruction
Before using OnLite, you must initialize Onlite with the getInstance method in the OnLiteFactory. OnLite to achieve some of the more complex queries, such as in accordance with a field ascending order or paged paging query.
### Sample Code
initial OnLiteFactory
```Java
    OnLiteFactory.getInstance("/sdcard/Android/data/package/db");
```
Create javaBean, annotate table name and buile project, automatically generate Lite class. There are 6 annotations to better create tables, its significance is just like its name. Except Table, other annotations are not required.(@AutoInc/@Column/@Ignore/@NotNull/@Table/@Unique)
```Java
@Table("user")
public class User {
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

get a reference to the data table
```Java
    UserLite userLite = OnLiteFactory.create(UserLite.class);
```
insert table

```Java
    User user = new User();
    user.setPswd("admin");
    user.setUsername("admin");
    userLite.insert(user)
```
delete all
```Java
    userLite.delete(null);
```
delete some data which you want
```Java
	User where=new User();
	where.setUsername("admin");
	userLite.delete(where);
```
delete table
```Java
    userLite.deleteTable();
```
updata some data which you want
```Java
    User user = new User();
    user.setPswd("admin");
    user.setUsername("1233");
    User where=new User();
    where.setUsername("admin");
    userLite.updata(user,where);
```
updata some data which you want, if not exist , will insert it
```Java
    User user = new User();
    user.setPswd("admin");
    user.setUsername("1233");
    User where=new User();
    where.setUsername("admin");
    userLite.updataOrInsert(user,where);
```
select some data
```Java
    /**
     * @param where condition
     * @param limit The number of data
     * @param page page number
     * @param orderColumnName Ascending or descending corresponding to the field or descending corresponding to the field
     * @param asc if true,ascending
     * @return list<User>
     */
    userLite.select(T where, Integer limit, Integer page, String orderColumnName, Boolean asc);

```
## How to
To get a Git project into your build:
### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories.[click here for details](https://github.com/hacknife/CarouselBanner/blob/master/root_build.gradle.png)

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### Step 2. Add the dependency
Add it in your application module build.gradle at the end of dependencies where you want to use.   [click here for details](https://github.com/hacknife/CarouselBanner/blob/master/application_build.gradle.png)
```Java
	dependencies {
	  ...
          compile 'com.github.hacknife.onlite:onlite:v1.2.7'
          annotationProcessor 'com.github.hacknife.onlite:onlite-compiler:v1.2.7'
	}
```
### Step 3. Add the permission
Add it in your application AndroidManifest.xml in the manifest label.   [click here for details](https://github.com/hacknife/OnHttp/blob/master/androimanifest.png)
```Java
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
## Thank you for your browsing
If you have any questions, please join the QQ group. I will do my best to answer it for you. Welcome to star and fork this repository, alse follow me.
<br>
![Image Text](https://github.com/hacknife/CarouselBanner/blob/master/qq_group.png)

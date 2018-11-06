# OnLite
[![](https://img.shields.io/badge/platform-android-orange.svg)](https://github.com/hacknife) [![](https://img.shields.io/badge/language-java-yellow.svg)](https://github.com/hacknife) [![](https://jitpack.io/v/com.hacknife/onlite.svg)](https://jitpack.io/#com.hacknife/onlite) [![](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/hacknife) [![](https://img.shields.io/badge/license-apache--2.0-green.svg)](https://github.com/hacknife) [![](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/hacknife)<br/><br/>
OnLite是Android的数据库框架，你不需要写任何的sql语句。 Onlite实现数据库的数据记录和java对象之间的映射。增加，删除，查询，修改，都可以通过java对象来实现。
## 特点
* 常规操作免手写sql
* 支持复杂数据类型储存
* 对象即数据记录
* 支持升序、降序、分页
* 支持增删改查
## 使用说明
在使用OnLite之前，必须先通过OnLiteFactory中的init方法初始化Onlite。OnLite实现了一些比较复杂的查询，比如按照某个字段升序或者倒序分页查询等。
### 代码示例
初始化OnLiteFactory，并设置数据库储存的位置
```Java
    OnLiteFactory.init("/sdcard/Android/data/package/db");
```
创建javabean，注解表名和build project，会自动生成Lite类。有6个注解能够更好的创建表，其意义正如其名，除了Table以外，其他注解都不是必须的。(@AutoInc/@Column/@Ignore/@NotNull/@Table/@Unique)
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

获Lite的对象
```Java
    UserLite userLite = OnLiteFactory.create(UserLite.class);
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
     * @param where 查询条件
     * @param limit 返回的数据条数 
     * @param page page number 页码
     * @param orderColumnName 用来排序的字段 
     * @param asc  为真，表示升序查询
     * @return list<User>
     */
    userLite.select(T where, Integer limit, Integer page, String orderColumnName, Boolean asc);
```
## 如何配置
将本仓库引入你的项目:
### Step 1. 添加仓库到Build文件
合并以下代码到项目根目录下的build.gradle文件的repositories尾。[点击查看详情](https://github.com/hacknife/CarouselBanner/blob/master/root_build.gradle.png)

	allprojects {
		repositories {
			...
			maven { url 'https://hacknife.com' }
		}
	}

### Step 2. 添加依赖
合并以下代码到需要使用的application Module的dependencies尾,注意替换version版本号。[点击查看详情](https://github.com/hacknife/CarouselBanner/blob/master/application_build.gradle.png)
```Java
	dependencies {
	  ...
          compile 'com.hacknife.onlite:onlite:version'
          annotationProcessor 'com.hacknife.onlite:onlite-compiler:version'
	}
```
### Step 3. 添加权限
合并以下代码到应用的AndroidManifest.xml的manifest标签中。[点击查看详情](https://github.com/hacknife/OnHttp/blob/master/androimanifest.png)
```Java
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
## 感谢浏览
如果你有任何疑问，请加入QQ群，我将竭诚为你解答。欢迎Star和Fork本仓库，当然也欢迎你关注我。
<br>
![Image Text](https://github.com/hacknife/CarouselBanner/blob/master/qq_group.png)

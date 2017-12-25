# OnLite  [![](https://jitpack.io/v/aliletter/onlite.svg)](https://jitpack.io/#aliletter/onlite)
OnLite是Android的数据库框架，你不需要写任何的sql语句。 Onlite实现数据库的数据记录和java对象之间的映射。增加，删除，查询，修改，都可以通过java对象来实现。
## 使用说明
在使用OnLite之前，必须先通过OnLiteFactory中的getInstance方法初始化Onlite。OnLite实现了一些比较复杂的查询，比如按照某个字段升序或者倒序分页查询等。
### 代码示例
初始化OnLiteFactory，并设置数据库储存的位置
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
创建Lite类，通过lite才能操作数据库
```Java
public class UserLite extends OnLite<User> {
}
```
获Lite的对象
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
     * @param where 查询条件
     * @param conditions 自定义的查询条件集合 
     * @param limit 返回的数据条数 
     * @param page page number 页码
     * @param orderColumnName 用来排序的字段 
     * @param asc  为真，表示升序查询
     * @return list<User>
     */
    userLite.select(User where, List<Condition> conditions, Integer limit, Integer page, String orderColumnName, Boolean asc);
```
## 如何配置
将本仓库引入你的项目:
### Step 1. 添加JitPack仓库到Build文件
合并以下代码到项目根目录下的build.gradle文件的repositories尾。[点击查看详情](https://github.com/aliletter/CarouselBanner/blob/master/root_build.gradle.png)

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### Step 2. 添加依赖
合并以下代码到需要使用的application Module的dependencies尾。[点击查看详情](https://github.com/aliletter/CarouselBanner/blob/master/application_build.gradle.png)
```Java
	dependencies {
	  ...
          compile 'com.github.aliletter:onlite:v1.2.1'
	}
```
### Step 3. 添加权限
合并以下代码到应用的AndroidManifest.xml的manifest标签中。[点击查看详情](https://github.com/aliletter/OnHttp/blob/master/androimanifest.png)
```Java
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
## 感谢浏览
如果你有任何疑问，请加入QQ群，我将竭诚为你解答。欢迎Star和Fork本仓库，当然也欢迎你关注我。
<br>
![Image Text](https://github.com/aliletter/CarouselBanner/blob/master/qq_group.png)

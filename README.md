# DbLite
![](https://img.shields.io/badge/platform-android-orange.svg)
![](https://img.shields.io/badge/language-java-yellow.svg)
![](https://jitpack.io/v/iwdael/dblite.svg)
![](https://img.shields.io/badge/build-passing-brightgreen.svg)
![](https://img.shields.io/badge/license-apache--2.0-green.svg)
![](https://img.shields.io/badge/api-19+-green.svg)

DbLite是Android的数据库框架，你不需要写任何的sql语句。 Dblite实现数据库的数据记录和java对象之间的映射。增加，删除，查询，修改，都可以通过java对象来实现。
## 特点
* 常规操作免手写sql
* 支持复杂数据类型储存
* 对象即数据记录
* 支持升序、降序、分页
* 支持增删改查
* 提供储存单个对象
## 使用说明
在使用DbLite之前，必须先通过DbLiteFactory中的init方法初始化Dblite。DbLite实现了一些比较复杂的查询，比如按照某个字段升序或者倒序分页查询等。

|注解|功能|对象|默认属性|必须|
|:------:|:------:|:------:|:------:|:------:|
|@Table|生成数据表|类|类名|是|
|@Version|数据表版本，版本不一致会重新创建表|类|0|否|
|@AutoInc|主键并自增长|类成员变量|无|否|
|@Unique|约束唯一标识|类成员变量|无|否|
|@Column|字段名|类成员变量|变量名|是|
|@NotNull|字段不能为空|类成员变量|空|否|

### 代码示例
初始化DbLiteFactory，并设置数据库储存的位置
```
    DbLiteFactory.init(Context context, String directoryName);
```
列表数据中，储存对象中存在不支持属性时，需提供复杂属性与字符串间相互转换方法，例如
```
    @Convert
    fun convertSongDissToString(ints: Diss): String {
        return Gson().toJson(ints)
    }

    @Convert
    fun convertStringToSongDiss(content: String): Diss {
        return Gson().fromJson<Diss>(content, object : TypeToken<Diss>() {}.type)
    }
```
单个对象储存，需要用户手动实现对象与字符串之间的转换，例如：
```
    @Convert
    fun objectConvertString(key: String, value: Any): String = Gson().toJson(value)

    @Convert
    fun <T> stringConvertObject(clazz: Class<T>, content: String): T = Gson().fromJson(content, clazz)
```

### 列表数据
创建列表类，并自动生成Lite类，例如：创建Entity，自动生成EntityLite类
```
@Table
class Entity(
        @Column var name: String? = null,
        @Column var address: String? = null,
        @Column var country: String? = null
)
```
获Lite的对象
```
    val ilte = DbLiteFactory.create(EntityLite.class)
```
增删改查
```
    lite.insert(user)
    lite.delete()
    lite.updata()
    lite.select()

```
### 单个数据
储存
```
    DbLiteStore.hold(String key, Object value)
```
获取
```
    DbLiteStore.obtain(String key, T defaultT)
    DbLiteStore.obtain(String key, Class<T> clazz)
```
## 快速引入项目
合并以下代码到需要使用的module的dependencies。
```Java
	dependencies {
	  ...
          compile 'com.iwdael.dblite:dblite:$version'
          annotationProcessor 'com.iwdael.dblite:dblite-compiler:$version'
	}
```
## 感谢浏览
如果你有任何疑问，请加入QQ群，我将竭诚为你解答。欢迎Star和Fork本仓库，当然也欢迎你关注我。
<br>
![Image Text](https://github.com/iwdael/CarouselBanner/blob/master/qq_group.png)

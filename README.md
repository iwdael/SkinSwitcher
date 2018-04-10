# SkinSwitcher  [![](https://jitpack.io/v/blackchopper/skinswitcher.svg)](https://jitpack.io/#blackchopper/skinswitcher)
SkinSwitcher is a dynamic open-source framework for skin peeing. After successful skinning, SkinSwitcher can achieve the peeing effect without restarting the application. It also supports the dynamic replacement of most of the properties defined in the layout file. The skin resource comes from the external apk file, not the skin resources inside the app.[中文文档](https://github.com/blackchopper/SkinSwitcher/blob/master/README_CHINESE.md)
## Instruction
The attribute resources that need to be replaced must be referenced (@dimen/XXXXX), and the direct type (XXDP) cannot be changed. The properties of the skin change control must have corresponding methods. If not, developers can do the method.
### Code Sample
```Java
public class BaseActivity extends Activity{
    protected SkinFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory = new SkinFactory() {
            /**
             * Attribute filter
             * @param attrName textColor,background,drawable and so on.
             * @return if ture,that attribute is what we need to replace.
             */
            @Override
            public boolean onAttributeFilter(String attrName) {
                return attrName.contains("background");
            }

            /**
             * The skin switcher, when we set the skin package, the two methods (onAttributeFilter, onSwitcher) will be called.
             * @param view If the current Activity contains multiple controls that need to be changed, this method also corresponds to the number of times the user needs to determine the type of the view.
             * @param attrType This property is used to assist the user to determine the type of resource that the current view needs to replace. (string,mipmap,drawable,dimen,color and so on)
             * @param attrName Property name. This property is filtered through the onAttributeFilter method. The user can set the properties of the View by calling the corresponding method to achieve the purpose of skin change.（textColor,background,drawable and so on）
             * @param attrValue Attribute reference value.(@dimen/xxx,@color/xxxx,@mimap/xxxx and so on)
             * @param attrObj The value of the attribute read in the skin package.
             */
            @Override
            public void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
                view.setBackgroundColor((Integer) attrObj);
            }
        };
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), factory);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        factory.apply();
    }
}
```

```Java
public class BaseActivity extends Activity implements SkinFactory2.OnSkinFactory {
    protected SkinFactory2 factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory=new SkinFactory2();
        factory.setOnSkinFactory(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), factory);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        factory.apply();
    }

    @Override
    public boolean onAttributeFilter(String attrName) {
        return attrName.contains("background");
    }

    @Override
    public void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
        view.setBackgroundColor((Integer) attrObj);
    }
}

```
### Skin bag making
The skin package is apk, and the apk only needs to contain the resources used. The type and the name of resource cannot be changed. 
```Java
      SkinFactory.initSkinFactory(getApplicationContext());
      SkinFactory.loadSkinPackage("/sdcard/app-debug.apk");
```
## How to
To get a Git project into your build:
### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories.   [click here for details](https://github.com/blackchopper/CarouselBanner/blob/master/root_build.gradle.png)
```Java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### Step 2. Add the dependency
Add it in your application module build.gradle at the end of dependencies where you want to use.[click here for details](https://github.com/blackchopper/CarouselBanner/blob/master/application_build.gradle.png)
```Java
	dependencies {
                ...
	        compile 'com.github.blackchopper:skinswitcher:v1.0.2'
	}
```
![Image Text](https://github.com/blackchopper/SkinSwitcher/blob/master/skinswitcher.gif)
<br><br><br>
## Thank you for your browsing
If you have any questions, please join the QQ group. I will do my best to answer it for you. Welcome to star and fork this repository, alse follow me.
<br>
![Image Text](https://github.com/blackchopper/CarouselBanner/blob/master/qq_group.png)
 

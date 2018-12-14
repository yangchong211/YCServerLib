## YCGallery Gallery browsing and picture zooming controls

#### Catalogue introduction
- 0.How to use
- 1.This library advantage bright spot
- 2.Introduction to use
    - 2.1 picture zooming and sliding control
    - 2.2 support loading picture zooming control
    - 2.3 support ViewPager sliding gallery control
    - 2.4 use RecyclerView to realize gallery browsing
- 3.Attention points
    - 3.1 Project points
    - 3.2 Project difficult issues
    - 3.3 to be optimized
- 4.Effect display
- 5.Other presentations


### 0.How to use 
#### 0.1 Used
- Add this in your root build.gradle file (not your module build.gradle file): 
    ```
    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
    ```
- Then, add the library to your module build.gradle 
    ```
    implementation 'cn.yc:YCGalleryLib:1.1.5'
    ```

#### 0.2 function declaration
- Gallery browsing and picture zooming controls, can customize the maximum scale of zoom size, double-click when zooming in or down, adding a transition animation, so that the experience is better. The zoom control can be used in conjunction with ViewPager, and when the zoom control loads a large image, such as a picture above 2MB, it can be set to load the loading.


#### 0.3 About language
- [Chinese中文文档](https://github.com/yangchong211/YCGallery/blob/master/README_CH.md)
- [English英文文档](https://github.com/yangchong211/YCGallery/blob/master/README_CH.md)


#### 04 Case demonstration animation
- ![image](https://github.com/yangchong211/YCGallery/blob/master/image/progress.gif)



### 1.This library advantage bright spot
- There are two ways to support scaling, the first is to double click n times, and then double click to scale n times [that is, go back to the original image size]
- you can customize the maximum scale size of the zoom, the default is 2 times, This should not be set indiscriminately, if the setting parameter is less than 1 or more than 10, then the default value is 2 times
- double click to enlarge or shrink, adding a transition animation, so that the experience is better. At the same time, the magnified image area is close to the double click position
- both hands can gesture to zoom the picture size. In zoom mode, the finger length can drag the picture, not beyond the border. If you want to set no scaling, You can set the scaling ratio to 1
- zoom mode with more than 2 fingers on the screen [some programmers may slide with 5 fingers] and lift them down in any order without causing confusion or unsmoothness 
- The zoom control can be used in conjunction with ViewPager, Itundefineds great, and you can run down the demo and look at the effect
- when the zoom control loads a large image, such as a picture above 2MB, then it takes time to load the image, too. At this point, ZoomLayoutView supports loading
- developers can implement click events and long press events themselves, such as previous product requirements, click close zoom browsing pages, and this can also be achieved 
- by using RecyclerView to implement gallery effects. Glide very smoothly, support picture, video browse at the same time. Similar to his WeChatundefineds dynamic circle of friends
- after testing, the picture zooming control perfectly supports the combination of ViewPager,RecyclerView,ListView and so on.



### 2.Introduction to use
#### 2.1 picture zooming and sliding control
- In the layout
    ```
    <com.yc.cn.ycgallerylib.zoom.view.ZoomImageView
            android:id="@+id/image"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    ```
- Code setting
    ```
    imageView = findViewById(R.id.image);
    imageView.setOnZoomClickListener(new OnZoomClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    imageView.setOnZoomLongClickListener(new OnZoomLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    });
    imageView.setMaxScale(4);
    imageView.setImageResource(R.drawable.image1);
    //注意不要使用setBackground设置图片，它不支持缩放
    //imageView.setBackground();
    ```
- You can add optimization code as appropriate
    ```
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imageView!=null){
            //Reset all states, clear mask, stop all gestures, stop all animations
            imageView.reset();
        }
    }
    ```


#### 2.2 support loading picture zooming control
- In the layout
    ```
    <com.yc.cn.ycgallerylib.zoom.view.ZoomLayoutView
        android:id="@+id/zoomView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    ```
- Code setting
    ```
    zoomView = findViewById(R.id.zoomView);
    zoomView.setLoadingVisibility(true);
    Uri parse = Uri.parse("file:///android_asset/yc.png");
    Picasso.with(this)
            .load(parse)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(zoomView.getImageView(), new Callback() {
                @Override
                public void onSuccess() {
                    zoomView.setZoomViewVisibility(true);
                }
    
                @Override
                public void onError() {
    
                }
            });
    ```


#### 2.3 support ViewPager sliding gallery control
- In the layout
    ```
    <com.yc.cn.ycgallerylib.gallery.GalleryImageView
        android:id="@+id/scroll_gallery_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#000" />
    ```
- Code setting
    ```
    scrollGalleryView = findViewById(R.id.scroll_gallery_view);
    scrollGalleryView
            //设置viewPager底部缩略图大小尺寸
            .setThumbnailSize(200)
            //设置是否支持缩放
            .setZoom(true)
            //设置缩放的倍数，当不支持缩放时，设置该参数无效
            .setZoomSize(3)
            //设置是否隐藏底部缩略图
            .hideThumbnails(false)
            //设置FragmentManager
            .setFragmentManager(getSupportFragmentManager())
            //添加滑动事件，也可以不用添加
            .addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    
                }
    
                @Override
                public void onPageSelected(int position) {
                    scrollGalleryView.setCurrentItem(position);
                }
    
                @Override
                public void onPageScrollStateChanged(int state) {
    
                }
            })
            //添加单张图片
            .addBitmap(toBitmap(R.drawable.image1))
            .addBitmap(toBitmap(R.drawable.image2))
            .addBitmap(toBitmap(R.drawable.image3))
            .addBitmap(toBitmap(R.drawable.image4))
            .addBitmap(toBitmap(R.drawable.image5))
            .addBitmap(toBitmap(R.drawable.image6))
            .addBitmap(toBitmap(R.drawable.image7))
            //或者添加图片集合
            .addBitmap(list);
    //获取当前索引处
    int currentItem = scrollGalleryView.getCurrentItem();
    //获取控件Viewpager
    ViewPager viewPager = scrollGalleryView.getViewPager();
    ```


#### 2.4 use RecyclerView to realize gallery browsing
- In the layout
    ```
    <com.yc.cn.ycgallerylib.recyclerView.GalleryRecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#000" />
    ```
- Code setting
    ```
    mRecyclerView
            //设置滑动速度
            .setFlingSpeed(10000)
            //设置adapter
            .setDataAdapter(adapter)
            //设置选中的索引
            .setSelectedPosition(2)
            //设置横向或者竖向，注意需要限制传入类型
            .setOrientation(RecyclerView.HORIZONTAL)
            //设置滚动监听事件
            .setOnGalleryListener(new OnGalleryListener() {
                @Override
                public void onInitComplete() {
                    Log.e(TAG,"onInitComplete初始化完成");
                }
    
                @Override
                public void onPageRelease(boolean isNext,int position) {
                    Log.e(TAG,"释放的监听，释放位置:"+position +" 下一页:"+isNext);
                    if (isNext){
                        Log.e(TAG,"释放的监听，释放位置:"+position +" 下一页:"+isNext);
                    }else {
                        Log.e(TAG,"释放的监听，释放位置:"+position +" 上一页:"+isNext);
                    }
                }
    
                @Override
                public void onPageSelected(int position) {
                    Log.e(TAG,"释放的监听，释放位置:"+position +" 当前页:"+position);
                }
            })
            //装载
            .setUp();
    ```


### 3.Attention points
#### 3.1 Project points


#### 3.2 Project difficult issues
- 3.2.1 Slip-click events are more difficult to handle
    - when the touch event finger is raised or the slide is over, note that if the previous mode is in zoom mode, You also need to trigger a zoom end animation
    - when the touch event finger is pressed at the first point, the scroll mode is turned on, and the point at which the scroll begins is recorded. Scrolling mode is not allowed during matrix animation when the touch event is ACTION_POINTER_DOWN [on behalf of the user and then uses a finger to touch the screen, where there is already a touch point, " There is a new touch point], then stop all animations, switch to zoom mode, and save the zooming two fingers
    - when the finger is moving in the move, record the moving point, and then handle the zoom logic. When dealing with zooming, be careful to scale according to the image zoom center, and have the zoom center at the midpoint of the zoom point, so that the zoom center of the picture follows the finger zoom midpoint
- 3.2.2 Picture zooms frequently to create Matrix optimizations
    - create object pool classes. The memory jitter is prevented from frequent new objects. Because of the maximum length limit of the object pool, if the swallowing measure exceeds the object pool capacity, the jitter will still occur. 
    - you need to increase the object pool capacity at this point, but it takes up more memory.
- 3.2.3 Animation transition effect processing during Picture scaling and moving
    - the first animation, for the inertial fling, after the finger slips, adds the inertia attribute animation FlingAnimator, first to clean up the animation that may be currently being executed, Then the speed gradually decays, when the picture can not move, the animation stops
    - the second animation, for multi-finger operation zoom logic, add zooming attribute animation ScaleAnimator, build a zoom animation, Transform from one matrix to another. Pay attention to get the animation progress, then calculate the matrix interpolation according to the animation progress, and finally redraw, finally achieve the picture zoom effect
- 3.2.4 load a large image can be set to load loading
    - for loading a large picture, when the image is not visible, you can set the load loading, to display the picture when loaded successfully



#### 3.3 to be optimized
- 3.3.1 If the picture zoom control is invoked frequently, how to increase bitmap reuse, will it cause bitmap memory leak?
- 3.3.2 How to prevent too large Bitmap, from being loaded later can maxSize be added to control the size of images loaded into memory
- 3.3.3 Gif dynamic images are not supported for the time being, can this feature be added at a later stage
- 3.3.4 For loading super large picture, see some scheme on the net that can show the function of super large picture in block, how to realize it needs to be studied in detail.
- 3.3.5 For picture zooming and frequent operation, the essence is still implemented through Matrix. In order to prevent memory jitter from frequent new objects, the size of the object pool should be set properly.
    - To resolve, create a matrix object pool MatrixPool, to add the created Matrix object to the object pool queue
    - When you get an Matrix object, if the object pool is empty, the object pool itself returns a new, and if there is an object in the object pool, an existing return is taken.
    - In order to prevent memory jitter and leakage, after the atrix object is used up, the object requested in the object pool is returned. If the number of returned objects exceeds the capacity of the object pool, the returned object will be discarded



### 4.Effect display
![image](https://github.com/yangchong211/YCGallery/blob/master/image/1.png)
![image](https://github.com/yangchong211/YCGallery/blob/master/image/2.jpg)
![image](https://github.com/yangchong211/YCGallery/blob/master/image/3.jpg)
![image](https://github.com/yangchong211/YCGallery/blob/master/image/4.jpg)
![image](https://github.com/yangchong211/YCGallery/blob/master/image/5.jpg)



### 5. Other presentations
#### About zhifubao
![image](https://upload-images.jianshu.io/upload_images/4432347-7100c8e5a455c3ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



####  About blog Summary links
- 1.[Technology blog summary](https://www.jianshu.com/p/614cb839182c)
- 2.[Open source project summary](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[Life blog summary](https://blog.csdn.net/m0_37700275/article/details/79832978)
     


#### About recommend
- Great summary of blog notes, including Java basics and in-depth knowledge, Android technology blog, Python learning notes, and so on, but also includes the usual development of bug summary, of course, also collected a large number of interview questions after work. Long-term update, maintenance and revision, continuous improvement. Open source files are in markdown format!
- chained address ： https://github.com/yangchong211/YCBlogs
- If you feel good, you can star, thank you! Of course, you are also welcome to put forward suggestions, everything starts at a slight, quantitative change causes qualitative change!



#### About LICENSE
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```




















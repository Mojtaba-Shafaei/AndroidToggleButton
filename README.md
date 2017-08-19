# **Android Toggle Button**

A customizable Android library to display Toggle Button.

## **Version**
1.0.0
### **Requirements**

- MIN-SDK Version = 15

### **Install**
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

    dependencies {
            compile 'com.github.Mojtaba-Shafaei:AndroidToggleButton:lastVersion'
    }



[![](https://jitpack.io/v/Mojtaba-Shafaei/AndroidToggleButton.svg)](https://jitpack.io/#Mojtaba-Shafaei/AndroidToggleButton)

### **Screenshots**

![samples](images/Untitled.png)

### **Sample code**

```xml
<com.mojtaba_shafaei.android.ToggleButton
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="40dp"
    app:tb_A_text="'A' Button"
    app:tb_B_text="'B' Button"
    app:tb_isMandatory="true"
    app:tb_layout_direction="ltr"
    app:tb_title="Title..."
    app:tb_titleTextSize="@dimen/titleTextSize" />
```

```java
    toggleButton.setTitleTypeface(typeface);
    toggleButton.setErrorEnabled(true);
    toggleButton.setError(R.string.errorText);

```
<!-- ## **Sample App**
[Download sample app from my drive](https://drive.google.com/file/d/0B7U-LJJvftlSZC1qRDcxeVV3N3M/view?usp=sharing) -->

## **Developer**

* **Mojtaba Shafaei** [Email](mjtb.shafaei@gmail.com)

## **License**
This project is licensed under the MIT License

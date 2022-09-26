## Compose Persian Date Picker - Alpha Version
Persian date picker for Compose, Based on Jalali calendar.
This library is designed for Iranians and other Persian speaking communities that utilize Jalali (Persian) calendar rather than Christian calendar which is the default calendar for Googles provided date picker composable in Android.

The design is based on Material guidlines. All the colors and fonts used in this composable are from Material theme of the app. The colors consist of "primary", "onPrimary", "primaryVariant", "backgound", "onBackground".
The fonts are also predefined fonts in Material theme, namely body1-2, h5-h1 and subtitle1-2. 

In order to customize the composable design you just need to modify your app's theme.

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://s6.uupload.ir/files/main_spdi.jpg">
  <source media="(prefers-color-scheme: light)" srcset="https://s6.uupload.ir/files/nightmode_4pz.jpg">
        <source media="(prefers-color-scheme: light)" srcset="https://s6.uupload.ir/files/months_xt33.jpg">
  <img alt="" src="https://s6.uupload.ir/files/main_spdi.jpg">
</picture>



## Adding to your project
First add `jitpack.io` to setting.gradle
```groovy
repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
```

Then add following line to your module build.gradle file
```groovy
dependencies{
  ...
  implementation 'com.github.Mohamad-Sarfi:compose_persian_date_picker:0.1.0'
 }
```

## Usage Instructions
The composable function "PersianDatePicker" must be called from another @Composable function.
First you have to declare a boolean state named "hideDatePicker" which its value will be set true in "onDismiss" lambda.

The picked date by user is return in "setDate" lambda. setDate has a Map<String, String> as parameter which has 3 keys, "day", "month" and "year". Each one can be accessed and stored the way shown below.

```kotlin
@Composable
fun MyCompose(){
  
  var hideDatePicker = mutableSateOf(true)
  
  Button(
    onClick = {hideDatePicker.value = false}
   ) {
    Text("انتخاب تاریخ")
   }
  
  if (!hideDatePicker){
  
    // *************************************************
    PersianDatePicker(
      onDismiss = { hideDatePicker.value = true }, 
      setDate = { date ->
          var day = date["day"]
          var month = date["month"]
          var year = date["year"]
        }
    )
    // *************************************************
    
  }
 
}
```

There are a couple of arbitrary parameters provieded as well in order to customize the composable:
```kotlin
@Composable
fun MyCompose(){
  
  var hideDatePicker = mutableSateOf(false)
  
  PersianDatePicker(
    minYear = 1350,
    maxYear = 1430,
    positiveButtonTxt= "تایید",
    negativeButtonTxt = "لغو,
    onDismiss = { hideDatePicker.value = true }, 
    setDate = { date ->
        var day = date["day"]
        var month = date["month"]
        var year = date["year"]
      }
    )
}
```
### Parameters Table
| Parameter | Description |
| --- | --- |
|`onDismiss`| Needs to set the value of a mutable state to `true` to hide the dialog | 
| `setDate` | returns a Map as the parameter of the lambda which consists of `it["day"]`,  `it["month"]`,  `it["year"]`
| `minYear` | sets minimum value of the year, default value is 1350 |
| `maxYear` | sets maximum value of the year, default value is 1420 |
| `positiveButtonTxt` | sets the text for positive button, default value is تایید |
| `negativeButtonTxt` | sets the text for negative button, default value is لغو |

If you face any issues or have feedbacks I'd be happy to hear from you `sarfimohamad46@gmail.com`




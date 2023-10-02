## Setup

- Dùng terminal hoặc cmd trỏ đến 📁BackendTemplate → chạy lệnh sau : 
>mvn install:install-file -Dfile=backendgame.com-1.0.jar -DgroupId=backendgame.com -DartifactId=backendgame.com -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true

- Build maven : 
> mvn clean install -DskipTests

- Cấu hình Eclipse cho Macos : `Eclipse` → `Preferences` → expand thẻ `Java`
	- `Compiler` : đổi sang 1.8
 	- `Installed JREs` : chọn đúng đường dẫn java đã cài (nếu chưa có thì `Add`)
	- `Installed JREs` → `Execution Environments`
		1. `Execution Enviroment` → chọn 1.8
		2. `Compatible JREs` → :white_check_mark: đúng phiên bản JRE đã setup

## Kiểu dữ liệu trong `BackendGame`
| Tên  | Giá trị  | Kích thước |
| :------------ | ---------------: | -----:|
| BOOLEAN | 1 | 1 byte |
| BYTE | 10 | 1 byte |
| STATUS | 11 | 1 byte |
| PERMISSION | 12 | 1 byte |
| AVARTAR | 13 | 1 byte |
| Gender | 14 | 1 byte |
| UnsignedByte | 19 | 1 byte |
| _______________ | ___ | ______ |
| SHORT | 20 | 2 byte |
| CountryCode | 21 | 2 byte |
| UnsignedShort | 39 | 2 byte |
| _______________ | ___ | ______ |
| INTEGER | 40 | 4 byte |
| IPV4 | 42 | 4 byte |
| UnsignedInteger | 59 | 4 byte |
| _______________ | ___ | ______ |
| FLOAT | 60 | 4 byte |
| _______________ | ___ | ______ |
| LONG | 80 | 8 byte |
| RANKING | 85 | 8 byte |
| USER_ID | 86 | 8 byte |
| TIMEMILI | 88 | 8 byte |
| UnsignedLong | 89 | 8 byte |
| _______________ | ___ |   ______ |
| DOUBLE | 90 | 8 byte |
| _______________ | ___ |   ______ |
| BYTE_ARRAY | 100 | N/A |
| List | 101 | N/A |
| _______________ | ___ |   ______ |
| LIST_Object | 110 | N/A |
| LIST_Boolean | 111 | N/A |
| LIST_Byte | 112 | N/A |
| LIST_Short | 113 | N/A |
| LIST_Integer | 114 | N/A |
| LIST_Float | 115 | N/A |
| LIST_Long | 116 | N/A |
| LIST_Double | 117 | N/A |
| LIST_String | 118 | N/A |
| _______________ | ___ |   ______ |
| IPV6 | 120 | 16 byte |
| _______________ | ___ |   ______ |
| STRING | 126 | N/A |



## Cass `BGColumn`
 - OffsetRow : vị trí của data
 - ViewId : thứ tự hiển thị ở client
 - Indexing : đánh dấu là cột này có được đánh index hay không
 - Size : số byte cần lưu của cột
 - Type : kiểu dữ liệu của cột (xem ở trên↑)
 - ColumnName : Tên cột


<br /><br />

### $${\color{yellow}➀ Primary1Key}$$
<p align="center">Table 1 khóa chính</p>

| Varibale  | Offset  | Value  | Mô tả |
| :------------------ | -:| ------------------: | :-------------|
| Offset_DatabaseType | 0 | 1 | Loại database : DBTYPE_1Primary=1 |
| Offset_AccessKey | 100 | ...(long) | Edit + Remove |
| Offset_ReadKey   | 108 | ...(long) | Read only |
| Offset_WriteKey  | 116 | ...(long) | Edit |
| Offset_LogoutId  | 125 | ...(byte) | Đúng id này thì mới tương tác với table được |
| ___________________ | ___ | ___ | __________________________ |
| Offset_Primary_Type | 512 | ... | Kiểu dữ liệu của khóa chính |
| Offset_Primary_Name | 514 | ... | Tên cột khóa chính |
| HEADER | 1024 | Describe column | Mô tả cột |

<br /><br />



#### $${\color{yellow}➁ Primary2Key}$$
<p align="center">Table 2 khóa chính</p>

| Varibale  | Offset  | Value  | Mô tả |
| :------------------ | -:| ------------------: | :-------------|
| Offset_DatabaseType | 0 | 2 | Loại database : DBTYPE_2Primary=2 |
| Offset_AccessKey | 100 | ...(long) | Edit + Remove |
| Offset_ReadKey   | 108 | ...(long) | Read only |
| Offset_WriteKey  | 116 | ...(long) | Edit |
| Offset_LogoutId  | 125 | ...(byte) | Đúng id này thì mới tương tác với table được |
| ___________________ | ___ | ___ | __________________________ |
| Offset_Primary_Type1 | 512 | ... | Kiểu dữ liệu của khóa chính 1 |
| Offset_Primary_Type2 | 513 | ... | Kiểu dữ liệu của khóa chính 2 |
| Offset_Primary_Name1 | 514 | ...(String) | Tên cột khóa chính 1 |
| Offset_Primary_Name2 | Sau String của Name1 | ...(String) | Tên cột khóa chính 2 |
| HEADER | 1024 | Describe column | Mô tả cột |

<br /><br />



### $${\color{yellow}📊 Row}$$
<p align="center">Table không có khóa chính → định danh bởi RowId</p>

| Varibale  | Offset  | Value  | Mô tả |
| :------------------ | -:| ------------------: | :-------------|
| Offset_DatabaseType | 0 | 3 | Loại database : DBTYPE_Row=3 |
| Offset_AccessKey | 100 | ...(long) | UserId sẽ edit được RowId |
| Offset_ReadKey   | 108 | ...(long) | Read only |
| Offset_WriteKey  | 116 | ...(long) | Edit tất cả row |
| Offset_LogoutId  | 125 | ...(byte) | Đúng id này thì mới tương tác với table được |

<br /><br />



### $${\color{yellow}🏆 Leaderboard Link}$$
<p align="center">Đây là Leaderboard link giá trị `TOP`. Mỗi row của table này gồm : UserId và Offset của Value cần hiển thị</p>

| Varibale  | Offset  | Value  | Mô tả |
| :------------------ | -:| ------------------: | :-------------|
| Offset_DatabaseType | 0 | 2 | Loại database : DBTYPE_2Primary=2 |
| Offset_AccessKey | 100 | ...(long) | Edit + Remove |
| Offset_ReadKey   | 108 | ...(long) | Read only |
| Offset_WriteKey  | 116 | ...(long) | Edit |
| Offset_LogoutId  | 125 | ...(byte) | Đúng id này thì mới tương tác với table được |
| ___________________ | ___ | ___ | __________________________ |
| Offset_numberTop  | 512 | ...(int) | number top |
| OffsetPrimary_DatabaseId  | 600 | ...(short) | DatabaseId |
| OffsetPrimary_TableId  | 602 | ...(short) | TableId |
| OffsetPrimary_ColumnId  | 604 | ...(int) | ColumnId |
| Offset_numberTop  | 512 | ...(int) | number top |
| Offset_numberTop  | 512 | ...(int) | number top |
 - User luôn post `Value` lên để so sánh với `MinValue` của `Table`
 - Các row trong `Table` này chỉ là link. Giá trị nằm ở vị trí mô tả : DatabaseId → TableId → Offset
 - `MinValue` của `Table` sẽ được cập nhật thông qua :
	- Lúc có Top mới → xử lý xong → gọi hàm `UpdateMinValue`
	- Người dùng gọi hàm `UpdateMinValue`

<br /><br />



### $${\color{yellow}🏆 Leaderboard Commit}$$
<p align="center">Dữ liệu do user Push lên, server không khống chế được</p>

| Varibale  | Offset  | Value  | Mô tả |
| :------------------ | -:| ------------------: | :-------------|
| Offset_DatabaseType | 0 | 2 | Loại database : DBTYPE_2Primary=2 |
| Offset_AccessKey | 100 | ...(long) | Edit + Remove |
| Offset_ReadKey   | 108 | ...(long) | Read only |
| Offset_WriteKey  | 116 | ...(long) | Edit |
| Offset_LogoutId  | 125 | ...(byte) | Đúng id này thì mới tương tác với table được |
| ___________________ | ___ | ___ | __________________________ |

<br /><br />



### $${\color{yellow}📜 Log}$$














### Backend Template : Features

- Realtime protocol + Auto Reconnect (wifi-3G-4G-5G)
- Restful api for game development
- Binary API for game development with high performance
- Database Lite in Backend (database mirroring : update in next version)

# Editor.md

![](https://pandao.github.io/editor.md/images/logos/editormd-logo-180x180.png)

![](https://img.shields.io/github/stars/pandao/editor.md.svg) ![](https://img.shields.io/github/forks/pandao/editor.md.svg) ![](https://img.shields.io/github/tag/pandao/editor.md.svg) ![](https://img.shields.io/github/release/pandao/editor.md.svg) ![](https://img.shields.io/github/issues/pandao/editor.md.svg) ![](https://img.shields.io/bower/v/editor.md.svg)


**Table of Contents**

[TOCM]

[TOC]

#H1 header
##H2 header
###H3 header
####H4 header
#####H5 header
######H6 header
#Heading 1 link [Heading link](https://github.com/pandao/editor.md "Heading link")
##Heading 2 link [Heading link](https://github.com/pandao/editor.md "Heading link")
###Heading 3 link [Heading link](https://github.com/pandao/editor.md "Heading link")
####Heading 4 link [Heading link](https://github.com/pandao/editor.md "Heading link") Heading link [Heading link](https://github.com/pandao/editor.md "Heading link")
#####Heading 5 link [Heading link](https://github.com/pandao/editor.md "Heading link")
######Heading 6 link [Heading link](https://github.com/pandao/editor.md "Heading link")

##Headers (Underline)

H1 Header (Underline)
=============

H2 Header (Underline)
-------------

###Characters
                
----

~~Strikethrough~~ <s>Strikethrough (when enable html tag decode.)</s>
*Italic*      _Italic_
**Emphasis**  __Emphasis__
***Emphasis Italic*** ___Emphasis Italic___

Superscript: X<sub>2</sub>，Subscript: O<sup>2</sup>

**Abbreviation(link HTML abbr tag)**

The <abbr title="Hyper Text Markup Language">HTML</abbr> specification is maintained by the <abbr title="World Wide Web Consortium">W3C</abbr>.

###Blockquotes

> Blockquotes

Paragraphs and Line Breaks
                    
> "Blockquotes Blockquotes", [Link](http://localhost/)。

###Links

[Links](http://localhost/)

[Links with title](http://localhost/ "link title")

`<link>` : <https://github.com>

[Reference link][id/name] 

[id/name]: http://link-url/

GFM a-tail link @pandao

###Code Blocks (multi-language) & highlighting

####Inline code

`$ npm install marked`

####Code Blocks (Indented style)

Indented 4 spaces, like `<pre>` (Preformatted Text).

    <?php
        echo "Hello world!";
    ?>
    
Code Blocks (Preformatted text):

    | First Header  | Second Header |
    | ------------- | ------------- |
    | Content Cell  | Content Cell  |
    | Content Cell  | Content Cell  |

####Javascript　

```javascript
function test(){
	console.log("Hello world!");
}
 
(function(){
    var box = function(){
        return box.fn.init();
    };

    box.prototype = box.fn = {
        init : function(){
            console.log('box.init()');

			return this;
        },

		add : function(str){
			alert("add", str);

			return this;
		},

		remove : function(str){
			alert("remove", str);

			return this;
		}
    };
    
    box.fn.init.prototype = box.fn;
    
    window.box =box;
})();

var testBox = box();
testBox.add("jQuery").remove("jQuery");
```

####HTML code

```html
<!DOCTYPE html>
<html>
    <head>
        <mate charest="utf-8" />
        <title>Hello world!</title>
    </head>
    <body>
        <h1>Hello world!</h1>
    </body>
</html>
```

###Images

Image:

![](https://pandao.github.io/editor.md/examples/images/4.jpg)

> Follow your heart.

![](https://pandao.github.io/editor.md/examples/images/8.jpg)

> 图为：厦门白城沙滩 Xiamen

图片加链接 (Image + Link)：

[![](https://pandao.github.io/editor.md/examples/images/7.jpg)](https://pandao.github.io/editor.md/examples/images/7.jpg "李健首张专辑《似水流年》封面")

> 图为：李健首张专辑《似水流年》封面
                
----

###Lists

####Unordered list (-)

- Item A
- Item B
- Item C
     
####Unordered list (*)

* Item A
* Item B
* Item C

####Unordered list (plus sign and nested)
                
+ Item A
+ Item B
    + Item B 1
    + Item B 2
    + Item B 3
+ Item C
    * Item C 1
    * Item C 2
    * Item C 3

####Ordered list
                
1. Item A
2. Item B
3. Item C
                
----
                    
###Tables
                    
First Header  | Second Header
------------- | -------------
Content Cell  | Content Cell
Content Cell  | Content Cell 

| First Header  | Second Header |
| ------------- | ------------- |
| Content Cell  | Content Cell  |
| Content Cell  | Content Cell  |

| Function name | Description                    |
| ------------- | ------------------------------ |
| `help()`      | Display the help window.       |
| `destroy()`   | **Destroy your computer!**     |

| Item      | Value |
| --------- | -----:|
| Computer  | $1600 |
| Phone     |   $12 |
| Pipe      |    $1 |

| Left-Aligned  | Center Aligned  | Right Aligned |
| :------------ |:---------------:| -----:|
| col 3 is      | some wordy text | $1600 |
| col 2 is      | centered        |   $12 |
| zebra stripes | are neat        |    $1 |
                
----

####HTML entities

&copy; &  &uml; &trade; &iexcl; &pound;
&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot; 

X&sup2; Y&sup3; &frac34; &frac14;  &times;  &divide;   &raquo;

18&ordm;C  &quot;  &apos;

##Escaping for Special Characters

\*literal asterisks\*

##Markdown extras

###GFM task list

- [x] GFM task list 1
- [x] GFM task list 2
- [ ] GFM task list 3
    - [ ] GFM task list 3-1
    - [ ] GFM task list 3-2
    - [ ] GFM task list 3-3
- [ ] GFM task list 4
    - [ ] GFM task list 4-1
    - [ ] GFM task list 4-2

###Emoji mixed :smiley:

> Blockquotes :star:

####GFM task lists & Emoji & fontAwesome icon emoji & editormd logo emoji :editormd-logo-5x:

- [x] :smiley: @mentions, :smiley: #refs, [links](), **formatting**, and <del>tags</del> supported :editormd-logo:;
- [x] list syntax required (any unordered or ordered list supported) :editormd-logo-3x:;
- [x] [ ] :smiley: this is a complete item :smiley:;
- [ ] []this is an incomplete item [test link](#) :fa-star: @pandao; 
- [ ] [ ]this is an incomplete item :fa-star: :fa-gear:;
    - [ ] :smiley: this is an incomplete item [test link](#) :fa-star: :fa-gear:;
    - [ ] :smiley: this is  :fa-star: :fa-gear: an incomplete item [test link](#);
            
###TeX(LaTeX)
   
$$E=mc^2$$

Inline $$E=mc^2$$ Inline，Inline $$E=mc^2$$ Inline。

$$\(\sqrt{3x-1}+(1+x)^2\)$$
                    
$$\sin(\alpha)^{\theta}=\sum_{i=0}^{n}(x^i + \cos(f))$$
                
###FlowChart

```flow
st=>start: Login
op=>operation: Login operation
cond=>condition: Successful Yes or No?
e=>end: To admin

st->op->cond
cond(yes)->e
cond(no)->op
```

###Sequence Diagram
                    
```seq
Andrew->China: Says Hello 
Note right of China: China thinks\nabout it 
China-->Andrew: How are you? 
Andrew->>China: I am good thanks!
```

###End

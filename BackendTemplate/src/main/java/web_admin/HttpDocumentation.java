package web_admin;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import com.google.gson.Gson;

import backendgame.com.core.server.BackendSession;
import backendgame.com.core.server.http.BGDocumentation;
import backendgame.com.core.server.http.BGHttp_Get;
import backendgame.com.core.server.http.BGHttp_Post;
import backendgame.com.core.server.http.Http;
import backendgame.com.core.server.http.HttpDocument;
import server.Server_BackendGame;

public class HttpDocumentation extends BGHttp_Get{
	public String URL;
	private Object initHttpProcess(Class<?> cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object http = cls.getConstructor().newInstance();
		
		Field[] listField=http.getClass().getFields();
		for(Field f:listField)
			if(Modifier.isStatic(f.getModifiers())==false && f.get(http)==null)
				if(f.getType().isArray()) {
					Object myArray = Array.newInstance(f.getType().getComponentType(), 1);
					if(f.getType().getComponentType().isPrimitive()==false)
					    Array.set(myArray, 0, initHttpProcess(f.getType().getComponentType()));
					f.set(http, myArray);
				}else {
					if(f.getType().isPrimitive()) {
						//Không cần init
					}else if(f.getType()==String.class) {
						f.set(http, "");
					}else if(f.getType()==Object.class) {
						
					}else
						f.set(http, initHttpProcess(f.getType()));
				}
		return http;
	}
	
    @Override public String onHttp(BackendSession session) {
    	BGDocumentation documentation;
    	ArrayList<BGDocumentation> listDoc=new ArrayList<>();
    	
    	
    	HashMap<String, Integer> mapHeader = Server_BackendGame.serverWebAdmin.getSortingHeader();
    	HashMap<String, Class<? extends BGHttp_Post>> mapURL = Server_BackendGame.serverWebAdmin.getMapUrl();
    	
    	
//    	ServerHttp server = (ServerHttp) session.sv;
//    	HashMap<String, Integer> mapHeader = server.sortingHeader;
//    	HashMap<String, Class<? extends BGHttp_Post>> mapURL = server.mapPost;
    	
        Set<String> listKey = mapURL.keySet();
        Class<?> cls;
        HttpDocument doc;
        Http api;
        BGHttp_Post http;
        Gson gson = new Gson();
        try {
        	int idStringLength=0;
            for (String key : listKey) {
                cls = mapURL.get(key);
                http = (BGHttp_Post) initHttpProcess(cls);
                api = cls.getAnnotation(Http.class);
                doc = cls.getAnnotation(HttpDocument.class);
                
                if(api!=null && doc!=null) {
                	http.test_api_in_document();
					documentation = new BGDocumentation();
					if(isNullOrEmpty(doc.Header())) {
						documentation.Header = "Others";
						documentation.HeaderId = Integer.MAX_VALUE/2;
					}else {
						documentation.Header = doc.Header();
						if(mapHeader.containsKey(documentation.Header))
							documentation.HeaderId = mapHeader.get(documentation.Header);
					}
					
					documentation.Api = api.api();
					documentation.Id = doc.id();
					documentation.Label = cls.getSimpleName();
					documentation.Description = doc.Description();
					documentation.Link = doc.Link();
					documentation.Data = http;
					
					if(idStringLength < (documentation.Id+"").length())
						idStringLength = (documentation.Id+"").length();
					
                    listDoc.add(documentation);
                }
            }
            for(BGDocumentation d:listDoc)
            	d.addIdToLabel(idStringLength);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        listDoc.sort(new Comparator<BGDocumentation>() {@Override public int compare(BGDocumentation o1, BGDocumentation o2) {return o1.Id - o2.Id;}});
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        String API="";
//        String urlPrefix = "http://"+BGUtility.getIp().get(0)+":"+session.getServerPort();
//        for(BackendGameDocumentation doc:listDoc) {
//             if(isNullOrEmpty(doc.Header)==false)
//                 API = API + "<p>Header : "+doc.Header+"</p>\n";
//             API += "<p>Class : "+doc.Label+"</p>\n";
//             API += "<p>Url : "+urlPrefix+doc.Api+"</p>\n";
//             if(isNullOrEmpty(doc.Description)==false)
//                 API += "<p>Detail : "+doc.Description+"</p>\n";
//             API += "<p>"+doc.Data+"</p>\n\n\n\n\n";
//        }
        
        return "\n"
        		+ "\n"
        		+ "\n"
        		+ "\n"
        		+ "<!DOCTYPE html>\n"
        		+ "<html lang=\"en\">\n"
        		+ "<head>\n"
        		+ "    <meta charset=\"UTF-8\">\n"
        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
        		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
        		+ "    <title>Document</title>\n"
        		+ "    <style>\n"
        		+ "        html, body {\n"
        		+ "            height: 100%;\n"
        		+ "            background-color: #333333;\n"
        		+ "            overflow: hidden;"
        		+ "margin: 0;\n"
        		+ "            padding: 0;\n"
        		+ "        }\n"
        		+ "        .container {\n"
        		+ "            height: 98%;\n"
        		+ "            display: flex;\n"
        		+ "            overflow: hidden;\n"
        		+ "        }\n"
        		+ "        .sidebar{\n"
        		+ "            width: 30%;\n"
        		+ "            /* padding-top: 10px;\n"
        		+ "            padding-left: 10px;\n"
        		+ "            padding-right: 10px; */\n"
        		+ "				overflow:scroll;        }\n"
        		+ "        /* .sidebar ul{\n"
        		+ "            list-style-type: none;\n"
        		+ "            margin: 0;\n"
        		+ "            padding: 0;\n"
        		+ "            font-size: 20px;\n"
        		+ "        } */\n"
        		+ "        a{\n"
        		+ "            text-decoration: none;\n"
        		+ "            color: aliceblue;\n"
        		+ "        }\n"
        		+ "        .content {\n"
        		+ "            display: flex;\n"
        		+ "            flex-direction: column;\n"
        		+ "            width: 70%;\n"
        		+ "            background-color: #333333;\n"
        		+ "            overflow:hidden;         }\n"
        		+ "        .resreq{\n"
        		+ "            width: 100%;\n"
        		+ "            position: relative;\n"
        		+ "            overflow:auto; \n"
        		+ "            height: 98vh;\n"
        		+ "            display: flex;\n"
        		+ "            flex-direction: column;\n"
        		+ "            color: aliceblue;\n"
        		+ "            border: solid 1px ;scrollbar-width: none\n"
        		+ "        }\n"
        		+ "        .api1-child li{\n"
        		+ "            padding-left: 30px;\n"
        		+ "        }\n"
        		+ "        .title{\n"
        		+ "            font-size: larger;\n"
        		+ "            text-transform: capitalize;\n"
        		+ "            color: #3787e3;\n"
        		+ "        }\n"
        		+ "        h1{\n"
        		+ "            color: aliceblue;\n"
        		+ "        }\n"
        		+ "        #navbar {\n"
        		+ "        list-style: none;\n"
        		+ "        padding: 0;\n"
        		+ "        }\n"
        		+ "\n"
        		+ "        #navbar li {\n"
        		+ "        margin-bottom: 10px;\n"
        		+ "        }\n"
        		+ "\n"
        		+ "        #navbar .title {\n"
        		+ "        font-size: 20px;\n"
        		+ "        font-weight: bold;\n"
        		+ "        cursor: pointer;\n"
        		+ "        display: flex;\n"
        		+ "        justify-content: space-between;\n"
        		+ "        align-items: center;\n"
        		+ "        }\n"
        		+ "\n"
        		+ "        /* #navbar .title:hover {\n"
        		+ "        text-decoration: underline;\n"
        		+ "        } */\n"
        		+ "\n"
        		+ "        #navbar .sign {\n"
        		+ "        margin-left: 10px;\n"
        		+ "        }\n"
        		+ "\n"
        		+ "        #navbar .api1-child {\n"
        		+ "        margin-left: 100px;\n"
        		+ "        }\n"
        		+ "\n"
        		+ "        #navbar .api1-child li {\n"
        		+ "        margin-bottom: 5px;\n"
        		+ "        }\n"
        		+ "        .data{\n"
        		+ "            margin-left: 50px;\n"
        		+ "            list-style: none;\n"
        		+ "        }\n"
        		+ "        .postDataButton{\n"
        		+ "            background-color: #19c2ec;\n"
        		+ "            height: 30px;\n"
        		+ "            margin-left: 20px;\n"
        		+ "        }\n"
        		+ "        .titlecontent{\n"
        		+ "            margin-left: 40px;\n"
        		+ "        }\n"
        		+ "        .input{\n"
        		+ "            width: 100%;\n"
        		+ "            box-sizing: border-box;\n"
        		+ "            margin-top: 10px;\n"
        		+ "            /* margin-right: 80px; */\n"
        		+ "        }\n"
        		+ "        .outputresponse{\n"
        		+ "            background-color: #333333;\n"
        		+ "            color: aliceblue;\n"
        		+ "            margin-top: 10px;   \n"
        		+ "            width: 100%;\n"
        		+ "      }\n"
        		+ "      .arrow{\n"
        		+ "        display: flex;\n"
        		+ "        align-items: center;\n"
        		+ "        padding: 0 1px;\n"
        		+ "      }\n"
        		+ ".listapi{\n"
        		+ "        margin-top: 10px;\n"
        		+ "      }\n"
        		+ "      .textarea {\n"
        		+ "          display: flex;\n"
        		+ "          height: 89vh;\n"
        		+ "      } \n"
        		+ "      .linkTo{\n"
        		+ "        color: #3ca015;\n"
        		+ "        margin-left: 10px;\n"
        		+ "      }\n"
        		+ "      .active {\n"
        		+ "  opacity: 1;\n"
        		+ "  transition: opacity 2s ease-in-out;\n"
        		+ "}\n"
        		+ "\n"
        		+ ".sidebar-item {\n"
        		+ "  opacity: 0.5;\n"
        		+ "  transition: opacity 0.1s ease-in-out;\n"
        		+ "}\n"
        		+ "\n"
        		+ ".sidebar-item.active {\n"
        		+ "  opacity: 1;\n"
        		+ "}\n"
        		+ "        </style>\n"
        		+ "</head>\n"
        		+ "<body>\n"
        		+ "    <div class=\"container\">\n"
        		+ "        <div class=\"sidebar\">\n"
        		+ "            <div>\n"
        		+ "            </div>\n"
        		+ "            <ul id=\"navbar\">\n"
        		+ "            </ul>\n"
        		+ "        </div>\n"
        		+ "        <div class=\"content\">\n"
        		+ "            <div class=\"resreq\" id=\"request\">\n"
        		+ "            </div>\n"
        		+ "        </div>\n"
        		+ "    </div>\n"
        		+ "    <script>\n"
        		+ "        var listScene ="
        		+ gson.toJson(listDoc)+"\n"
        		+ "window.onload = function() {\n"
        		+ "  const scenesByHeaderId = listScene.reduce((acc, scene) => {\n"
        		+ "    if (!acc[scene.HeaderId]) {\n"
        		+ "      acc[scene.HeaderId] = [];\n"
        		+ "    }\n"
        		+ "    acc[scene.HeaderId].push(scene);\n"
        		+ "    return acc;\n"
        		+ "  }, {});\n"
        		+ "\n"
        		+ "  // Sort scenes by HeaderId\n"
        		+ "  const sortedScenes = Object.entries(scenesByHeaderId).sort((a, b) => a[0] - b[0]);\n"
        		+ "\n"
        		+ "  const sortedScenesWithIdClick = sortedScenes.map(([headerId, scenes]) => {\n"
        		+ "    return scenes.map((scene, index) => {\n"
        		+ "      return {\n"
        		+ "        ...scene,\n"
        		+ "        clickId: `${headerId}_${index + 1}`\n"
        		+ "      };\n"
        		+ "    });\n"
        		+ "  });\n"
        		+ "\n"
        		+ "  // Generate HTML for navbar\n"
        		+ "  const navbar = document.getElementById('navbar');\n"
        		+ "  const listApi = document.getElementById('request');\n"
        		+ "  navbar.innerHTML = sortedScenesWithIdClick.map((scenesGroup) => {\n"
        		+ "    const header = scenesGroup[0].Header;\n"
        		+ "    return `\n"
        		+ "      <li>\n"
        		+ "        <a href=\"#\" class=\"title\">${header} <span class=\"sign\">-</span></a>\n"
        		+ "        <ul class=\"hidden\">\n"
        		+ "          ${scenesGroup.map((scene) => {\n"
        		+ "            return `\n"
        		+ "              <li class=\"label\">\n"
        		+ "                <a href=\"#\" class=\"sidebar-item\" data-id=\"${scene.clickId}\" onclick=\"showContent('${scene.clickId}', '${scene.Api}', '${scene.Link}', '${scene.Id}', '${scene.View}', '${scene.Description}', '${encodeURIComponent(JSON.stringify(scene.Data))}')\">${scene.Label}</a>\n"
        		+ "              </li>\n"
        		+ "            `;\n"
        		+ "          }).join('')}\n"
        		+ "        </ul>\n"
        		+ "      </li>\n"
        		+ "    `;\n"
        		+ "  }).join('');\n"
        		+ "\n"
        		+ "  listApi.innerHTML = `<ul>\n"
        		+ "  ${listScene\n"
        		+ "    .sort((a, b) => a.Id - b.Id)\n"
        		+ "    .map((scene, index) => {\n"
        		+ "      const clickId = sortedScenesWithIdClick\n"
        		+ "        .flat()\n"
        		+ "        .filter((s) => s.Api === scene.Api)\n"
        		+ "        .map((s) => s.clickId)\n"
        		+ "        .join(',');\n"
        		+ "      return `<li onclick=\"showContent('${clickId}','${scene.Api}','${scene.Link}','${scene.Id}','${scene.View}','${scene.Description}', '${encodeURIComponent(JSON.stringify(scene.Data))}')\" class=\"listapi\">\n"
        		+ "        <a href=\"#\"> Api_${scene.Id} : ${scene.Api}</a>\n"
        		+ "      </li>`;\n"
        		+ "    })\n"
        		+ "    .join('')}\n"
        		+ "</ul>`;\n"
        		+ "\n"
        		+ "};\n"
        		+ "\n"
        		+ "\n"
        		+ "var dataObj = {};\n"
        		+ "\n"
        		+ "// Define updateData function to update the dataObj variable\n"
        		+ "function updateData(event) {\n"
        		+ "  const textareaValue = event.target.value;\n"
        		+ "  try {\n"
        		+ "    const updatedDataObj = JSON.parse(textareaValue);\n"
        		+ "    dataObj = { ...updatedDataObj };\n"
        		+ "    console.log(dataObj);\n"
        		+ "  } catch (error) {\n"
        		+ "    console.error('Invalid JSON format');\n"
        		+ "  }\n"
        		+ "}\n"
        		+ "\n"
        		+ "function showContent(clickId,url,link,id, view, detail, data) {\n"
        		+ "  const sidebarItems = document.querySelectorAll('.sidebar-item');\n"
        		+ "  sidebarItems.forEach((item) => {\n"
        		+ "    item.classList.remove('active');\n"
        		+ "  });\n"
        		+ "  const currentSidebarItem = document.querySelector(`.sidebar-item[data-id=\"${clickId}\"]`);\n"
        		+ "  console.log(`id: ${id}`);\n"
        		+ "  console.log(`currentSidebarItem: ${currentSidebarItem}`);\n"
        		+ "  currentSidebarItem.classList.add('active');\n"
        		+ "  currentSidebarItem.focus();\n"
        		+ "  currentSidebarItem.scrollIntoView({ behavior: 'smooth', block: 'center', scrollDuration: 5000 });\n"
        		+ "  var request = document.getElementById('request');\n"
        		+ "  dataObj = { ...JSON.parse(decodeURIComponent(data)) };\n"
        		+ "  console.log(dataObj);\n"
        		+ "\n"
        		+ "  var dataString = Object.entries(dataObj)\n"
        		+ "    .map(([key, value]) => `\\n'${key}':  '${value}'`)\n"
        		+ "    .join('\\n');\n"
        		+ "  request.innerHTML = `\n"
        		+ "    <ul style=\"list-style: none;\">\n"
        		+ "      <li style=\"font-size: 20px;\">\n"
        		+ "        Api_${id} : ${url}\n"
        		+ "        ${link ? `<a href=\"${link}\" target=\"_blank\" class=\"linkTo\">[Link]</a>` : ''}\n"
        		+ "        <button class=\"postDataButton\" onclick=\"postData('${url}')\">Send ⇨ </button>\n"
        		+ "      </li>\n"
        		+ "      ${detail&& `<li style=\"opacity: 0.5;\">  ${detail}  </li>`}\n"
        		+ "      <div class=\"textarea\">\n"
        		+ "        <textarea id=\"output\" cols=\"100\" rows=\"20\" class=\"input\" oninput=\"updateData(event)\">${JSON.stringify(dataObj, null, 2)}</textarea>\n"
        		+ "        <div class=\"arrow\"><h3> ⇨ </h3> </div>\n"
        		+ "        <textarea id=\"outputresponse\" class=\"outputresponse\" cols=\"100\" rows=\"20\" disabled ></textarea>\n"
        		+ "      </div>\n"
        		+ "      </ul>\n"
        		+ "  `;\n"
        		+ "}\n"
        		+ "\n"
        		+ "function postData(url) {\n"
        		+ "  fetch(url, {\n"
        		+ "    method: 'POST',\n"
        		+ "    headers: {\n"
        		+ "      'Content-Type': 'application/json'\n"
        		+ "    },\n"
        		+ "    body: JSON.stringify({ ...dataObj })\n"
        		+ "  })\n"
        		+ "    .then(response => {\n"
        		+ "      console.log('Response:', response);\n"
        		+ "      return response.json();\n"
        		+ "    })\n"
        		+ "    .then(parsedData => {\n"
        		+ "      var response = document.getElementById('outputresponse');\n"
        		+ "      var htmlContent = JSON.stringify(parsedData, null, 2);\n"
        		+ "      response.innerHTML = `${htmlContent}`;\n"
        		+ "    })\n"
        		+ "    .catch(error => {\n"
        		+ "      console.error('Error:', error);\n"
        		+ "    });\n"
        		+ "}\n"
        		+ "\n"
        		+ "function toggleContent(event) {\n"
        		+ "  const header = event.target;\n"
        		+ "  const content = header.nextElementSibling;\n"
        		+ "  const sign = header.querySelector('.sign');\n"
        		+ "\n"
        		+ "  if (content && content.tagName === 'UL') {\n"
        		+ "    const isHidden = content.classList.contains('hidden');\n"
        		+ "    content.classList.toggle('hidden');\n"
        		+ "    sign.innerText = isHidden ? '-' : '+';\n"
        		+ "    // Show or hide all labels related to the clicked header\n"
        		+ "    const labels = content.querySelectorAll('.label');\n"
        		+ "    labels.forEach((label) => {\n"
        		+ "      label.style.display = isHidden ? 'block' : 'none';\n"
        		+ "    });\n"
        		+ "  }\n"
        		+ "}\n"
        		+ "</script>\n"
        		+ "</body>\n"
        		+ "</html>";
        		
    }
    public boolean isNullOrEmpty(String s) {
        return s==null || s.equals("");
    }
}

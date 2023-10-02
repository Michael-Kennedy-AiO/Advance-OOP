document.addEventListener("DOMContentLoaded", loadDataNavbar);
const urlParams = new URLSearchParams(window.location.search);
const token = localStorage.getItem("token"); // Get the token from local storage
const dbId = urlParams.get("dbId");
const tbId = urlParams.get("tbId"); // Get the dbId from the URL parameter
const tbType = urlParams.get("tbType"); // Get the dbId from the URL parameter
function loadDataNavbar() {
  const token = localStorage.getItem("token"); // Get the token from local storage
  fetch("/admin/Lobby/Get-Database-Info", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      AdminToken: token, // Include the token in the request body with the name "AdminToken"
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.status === "Success") {
        const databaseList = data.data;

        localStorage.setItem("listTable", JSON.stringify(databaseList));
        generateListDB(databaseList);
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
function toggleDropdowntable(event) {
  const target = event.target;
  const parent = target.parentNode.parentNode.nextElementSibling;
  parent.classList.toggle("isactive");
}
const databaseList = JSON.parse(localStorage.getItem("listTable"));
function toggleDropdown() {
  const dropdownMenu = document.getElementById("dropdown-menu");
  dropdownMenu.classList.toggle("inactive");
}
function generateListDB(data) {
  const dropdownMenu = document.getElementById("dropdown-menu");
  dropdownMenu.innerHTML = ""; // Clear the dropdown menu before re-generating

  data.forEach((database) => {
    const { DatabaseName, DBId, listTable, AccountLogin } = database;

    const isActive = DBId == dbId ? "active" : "";
    const Active = DBId == dbId ? "isactive" : "";
    const listTableMenu = `
        <li class="dropdown-table">
          <div class="itemContainer ${isActive}" style="margin-bottom: 10px">
            <a href="database.html?dbId=${DBId}">${
      AccountLogin ? "⭐ " : "☆ "
    }${DatabaseName}</a>
            <div style="display: flex">
              <button onclick="openDialogCreate(${DBId})" style="color: #71BCDE; font-size:30px; text-shadow: 0 0 2px #000;" >+</button>
              <button onclick="navigateToConfigPage(${DBId})" style=" font-size:20px">⚙️</button>
              <button class="dropdown-toggle" onclick="toggleDropdowntable(event)" style="color: #71BCDE; font-size:15px">▼</button>
            </div>
          </div>
          <ul class="dropdown-menu-table ${Active}" style="margin-left: 10px">
            ${listTable
              .map((table) => {
                const { TableName, TableType, TableId } = table;
                const isActiveTable =
                  DBId == dbId && TableId == tbId ? "activetable" : "";

                const symbol = generateSymbol(TableType);
                return `
                  <li class="dropdown-table">
                    <div class="itemContainer ${isActiveTable}" style="margin-bottom: 10px">
                      <a href="Table.html?tbId=${TableId}&dbId=${DBId}&tbType=${TableType}" style="white-space: nowrap;">${symbol}  ${TableName}</a>
                      <div>
                        <button onclick="navigateToConfigTable2(${TableId}, ${DBId}, ${TableId})" style=" font-size:20px">⚙️</button>
                      </div>
                    </div>
                  </li>`;
              })
              .join("")}
          </ul>
        </li>`;
    const modal = `<dialog id="createtable" class="create-table">
        <table >
          <thead>
            <tr>
              <th style="width: 200px"></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr>
                <td><label for="tableName">Table Name:</label></td>
                <td><input type="text" id="tableName" name="tableName" class="input-field1"></td>
            </tr>
            <tr>
                <td> <label for="description">Description:</label></td>
                <td><input type="text" id="description" name="description" class="input-field1"></td>
            </tr>
            <tr>
            <td> <label for="typeSelect">Table Type:</label></td>
                <td><select id="typeSelect" onchange="tableTypeChange()" class="input-field1">
                <option value="1" >❶ 1Primary</option>
                <option value="2">❷ 2Primary</option>
                <option value="3">📄 Row</option>
                <option value="4">📍 LineNode</option>
                <option value="5">🏆 Leaderboard</option>
            </select>
            </td>
            </tr>
          </tbody>
        </table>
            </div>
            <div id="formCreateTable">
            <div> 
    <div>
        <table >
          <thead>
            <tr>
              <th style="width: 200px"></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr>
                <td><label for="primaryName">Primary Name:</label></td>
                <td> <input type="text" id="primaryName" name="primaryName" class="input-field1">
                <select id="primaryType1" class="input-field1">
                ${Object.entries(typeOptions)
                  .map(
                    ([value, { label }]) =>
                      `<option value="${value}">${label}</option>`
                  )
                  .join("")}
                </select>
                </td>
            </tr>
            <tr>
                <td>  <label for="viewIdPrimary">View ID:</label></td>
                <td> <input type="number" id="viewIdPrimary" name="viewIdPrimary" class="input-field1"></td>
            </tr>
          </tbody>
        </table>
    <table id="tableCreatePrimary">
          <thead>
            <tr>
            <th>ColumnName</th>
            <th>Type</th>
            <th>Size</th>
            <th>ViewId</th>
            <th>Indexing</th>
            <th>Properties</th>
            <th>DefaultValue</th>
              <th><button onclick="addNewRow()" style="color: #71BCDE; font-size:30px; text-shadow: 0 0 2px #000;" >+</button>
              </th>
            </tr>
          </thead>
          <tbody>

          </tbody>
        </table>
        <button onclick="Create1Primary()" class="createTableBtn">Create</button>
    </div></div>
            </div>
              <button onclick="closeDialogCreate()">Close</button>
            </dialog>`;
    dropdownMenu.innerHTML += listTableMenu;
    dropdownMenu.innerHTML += modal;
  });
}
function navigateToConfigTable2(tableID, databaseID, TableType) {
  window.location.href = `ConfigTable.html?tbId=${tableID}&dbId=${databaseID}&tbType=${TableType}`;
}
function openDialogCreate(DBid, DBName) {
  const dialog = document.getElementById("createtable");
  selectedDBid = DBid;
  dialog.showModal();
}
function closeDialogCreate() {
  const dialog = document.getElementById("createtable");
  dialog.close();
}

function tableTypeChange() {
  const tableType = document.getElementById("typeSelect").value;
  const formCreateTable = document.getElementById("formCreateTable");

  switch (tableType) {
    case "1":
      formCreateTable.innerHTML = `<div> 
      <div>
          <table>
            <thead>
              <tr>
                <th style="width: 200px"></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                  <td><label for="primaryName">Primary Name:</label></td>
                  <td> <input type="text" id="primaryName" name="primaryName" class="input-field1">
                  <select id="primaryType1" class="input-field1">
                  ${Object.entries(typeOptions)
                    .map(
                      ([value, { label }]) =>
                        `<option value="${value}">${label}</option>`
                    )
                    .join("")}
                  </select>
                  </td>
              </tr>
              <tr>
                  <td>  <label for="viewIdPrimary">View ID:</label></td>
                  <td> <input type="number" id="viewIdPrimary" name="viewIdPrimary" class="input-field1"></td>
              </tr>
            </tbody>
          </table>
      <table id="tableCreatePrimary">
            <thead>
              <tr>
              <th>ColumnName</th>
              <th>Type</th>
              <th>Size</th>
              <th>ViewId</th>
              <th>Indexing</th>
              <th>Properties</th>
              <th>DefaultValue</th>
                <th><button onclick="addNewRow()" style="color: #71BCDE; font-size:30px; text-shadow: 0 0 2px #000;" >+</button>
                </th>
              </tr>
            </thead>
            <tbody>
  
            </tbody>
          </table>
          <button onclick="CreatePrimary1Key()" class="createTableBtn">Create</button>
      </div></div>`;
      break;
    case "2":
      formCreateTable.innerHTML = `<div> 
      <div>
          <table>
            <thead>
              <tr>
                <th style="width: 200px"></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                  <td><label for="hashName">Hash name:</label></td>
                  <td> <input type="text" id="hashName" name="hashName" class="input-field1">
                  <select id="typeHash" class="input-field1">
                  ${Object.entries(typeOptions)
                    .map(
                      ([value, { label }]) =>
                        `<option value="${value}">${label}</option>`
                    )
                    .join("")}
                  </select>
                  </td>
              </tr>
              <tr>
              <td><label for="rangeName">Range name:</label></td>
              <td> <input type="text" id="rangeName" name="rangeName" class="input-field1">
              <select id="typeRange" class="input-field1">
              ${Object.entries(typeOptions)
                .map(
                  ([value, { label }]) =>
                    `<option value="${value}">${label}</option>`
                )
                .join("")}
              </select>
              </td>
          </tr>
          <tr>
          <td>  <label for="viewIdPrimary">View ID:</label></td>
          <td> <input type="number" id="viewIdPrimary" name="viewIdPrimary" class="input-field1"></td>
      </tr>
            </tbody>
          </table>
      <table id="tableCreatePrimary">
            <thead>
              <tr>
                <th>ColumnName</th>
                <th>Type</th>
                <th>Size</th>
                <th>ViewId</th>
                <th>Indexing</th>
                <th>Properties</th>
                <th>DefaultValue</th>
                <th><button onclick="addNewRow()" style="color: #71BCDE; font-size:30px; text-shadow: 0 0 2px #000;" >+</button>
                </th>
              </tr>
            </thead>
            <tbody>
  
            </tbody>
          </table>
          <button onclick="CreatePrimary2Key()" class="createTableBtn">Create</button>
      </div></div>`;
      break;
    default:
      formCreateTable.innerHTML = `csssss`;
      break;
  }
}
let selectedDBid;
function addNewRow() {
  const tableBody = document.querySelector("#tableCreatePrimary tbody");

  // Create a new row element
  const newRow = document.createElement("tr");

  // Generate the HTML for the columns
  const html = `
      <td><input type="text" name="columnName" class="input-field1"></td>
      <td>
        <select class="typeSelect1" onchange="updateSizeInput1(this)">
          ${Object.entries(typeOptions)
            .map(
              ([value, { label }]) =>
                `<option value="${value}">${label}</option>`
            )
            .join("")}
        </select>
      </td> 
      <td><input type="number" class="sizeInput1 input-field2"  readonly></td>
      <td><input type="number" name="viewId" class="input-field2"></td>
      <td><input type="checkbox" name="indexing" ></td>
      <td><input type="number" name="properties" class="input-field2"></td>
      <td><input type="text" name="defaultValue" class="input-field1"></td>
      <td><button onclick="deleteRow(this)">❌</button></td>

    `;

  // Set the HTML of the new row
  newRow.innerHTML = html;

  tableBody.appendChild(newRow);
}
function deleteRow(button) {
  const row = button.closest("tr");
  row.remove();
}
function updateSizeInput1(selectElement) {
  const row = selectElement.parentNode.parentNode;
  const sizeInput = row.querySelector(".sizeInput1");

  const selectedType = selectElement.value;
  const selectedOption = typeOptions[selectedType];

  if (selectedOption && selectedOption.size) {
    sizeInput.value = selectedOption.size;
    sizeInput.disabled = true;
  } else {
    sizeInput.value = "";
    sizeInput.disabled = false;
    sizeInput.removeAttribute("readonly");
  }
}

function CreatePrimary1Key() {
  const token = localStorage.getItem("token"); // Get the token from local storage
  const tableName = document.getElementById("tableName");
  const tableDescribtion = document.getElementById("description");
  const primaryName = document.getElementById("primaryName");
  const primaryType = document.getElementById("primaryType1");
  const viewIdPrimary = document.getElementById("viewIdPrimary");

  var dataJson = {
    PrimaryName: primaryName.value,
    PrimaryType: primaryType.value,
    Describes: [],
    DBId: selectedDBid,
    TableName: tableName.value,
    Description: tableDescribtion.value,
    ViewId: viewIdPrimary.value,
    AdminToken: token,
  };

  // Get table rows
  const tableRows = Array.from(
    document.querySelectorAll("#tableCreatePrimary tbody tr")
  );

  // Create an array to store the describes
  const describes = [];

  // Iterate over each table row
  tableRows.forEach((row) => {
    // Get the input values from each column
    const columnName = row.querySelector("[name=columnName]").value;
    const viewId = Number(row.querySelector("[name=viewId]").value);
    const indexing = row.querySelector("[name=indexing]").checked;
    const properties = Number(row.querySelector("[name=properties]").value);
    const size = Number(row.querySelector(".sizeInput1").value);
    const type = Number(row.querySelector(".typeSelect1").value);
    const defaultValue = row.querySelector("[name=defaultValue]").value;

    // Create an object for each row
    const describe = {
      ColumnName: columnName,
      ViewId: viewId,
      Indexing: indexing,
      Properties: properties,
      Size: size,
      Type: type,
      DefaultValue: defaultValue,
    };

    // Add the object to the describes array
    describes.push(describe);
  });

  // Add the describes array to the dataJson object
  dataJson.Describes = describes;
  console.log(dataJson);
  fetch("/admin/Table/Create/1Primary ", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(dataJson),
  })
    .then((response) => {
      loadDataNavbar();
      const dialog = document.getElementById("createtable");
      dialog.close();
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
function CreatePrimary2Key() {
  const token = localStorage.getItem("token"); // Get the token from local storage
  const tableName = document.getElementById("tableName");
  const tableDescribtion = document.getElementById("description");
  const hashName = document.getElementById("hashName");
  const typeHash = document.getElementById("typeHash");
  const rangeName = document.getElementById("rangeName");
  const typeRange = document.getElementById("typeRange");
  const viewIdPrimary = document.getElementById("viewIdPrimary");

  var dataJson = {
    HashName: hashName.value,
    TypeHash: typeHash.value,
    RangeName: rangeName.value,
    TypeRange: typeRange.value,
    Describes: [],
    DBId: selectedDBid,
    TableName: tableName.value,
    Description: tableDescribtion.value,
    ViewId: viewIdPrimary.value,
    AdminToken: token,
  };

  // Get table rows
  const tableRows = Array.from(
    document.querySelectorAll("#tableCreatePrimary tbody tr")
  );

  // Create an array to store the describes
  const describes = [];

  // Iterate over each table row
  tableRows.forEach((row) => {
    // Get the input values from each column
    const columnName = row.querySelector("[name=columnName]").value;
    const viewId = Number(row.querySelector("[name=viewId]").value);
    const indexing = row.querySelector("[name=indexing]").checked;
    const properties = Number(row.querySelector("[name=properties]").value);
    const size = Number(row.querySelector(".sizeInput1").value);
    const type = Number(row.querySelector(".typeSelect1").value);
    const defaultValue = row.querySelector("[name=defaultValue]").value;

    // Create an object for each row
    const describe = {
      ColumnName: columnName,
      ViewId: viewId,
      Indexing: indexing,
      Properties: properties,
      Size: size,
      Type: type,
      DefaultValue: defaultValue,
    };

    // Add the object to the describes array
    describes.push(describe);
  });

  // Add the describes array to the dataJson object
  dataJson.Describes = describes;
  console.log(dataJson);
  fetch("/admin/Table/Create/2Primary ", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(dataJson),
  })
    .then((response) => {
      loadDataNavbar();
      const dialog = document.getElementById("createtable");
      dialog.close();
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
function navigateToConfigPage(event, Id) {
  event.preventDefault();
  window.location.href = `Config.html?dbId=${Id}`;
}
const typeOptions = {
  1: { label: "BOOLEAN", size: 1 },
  10: { label: "BYTE", size: 1 },
  11: { label: "STATUS", size: 1 },
  12: { label: "PERMISSION", size: 1 },
  13: { label: "AVARTAR", size: 1 },
  14: { label: "Gender", size: 1 },
  20: { label: "SHORT", size: 2 },
  21: { label: "CountryCode", size: 2 },
  40: { label: "INTEGER", size: 4 },
  42: { label: "IPV4", size: 4 },
  60: { label: "FLOAT", size: 4 },
  80: { label: "LONG", size: 8 },
  88: { label: "USER_ID", size: 8 },
  89: { label: "TIMEMILI", size: 8 },
  90: { label: "DOUBLE", size: 8 },
  100: { label: "BYTE_ARRAY" },
  101: { label: "List" },
  110: { label: "LIST_Object" },
  111: { label: "LIST_Boolean" },
  112: { label: "LIST_Byte" },
  113: { label: "LIST_Short" },
  114: { label: "LIST_Integer" },
  115: { label: "LIST_Float" },
  116: { label: "LIST_Long" },
  117: { label: "LIST_Double" },
  118: { label: "LIST_String" },
  120: { label: "IPV6" },
  126: { label: "STRING" },
};
function generateSymbol(id) {
  switch (id) {
    case 1:
      return "❶";
    case 2:
      return "❷";
    case 3:
      return "📄";
    case 5:
      return "📍";
    case 8:
      return "🏆";
  }
}
function backToDatabase() {
  window.location.href = `Database.html?dbId=${dbId}`;
}

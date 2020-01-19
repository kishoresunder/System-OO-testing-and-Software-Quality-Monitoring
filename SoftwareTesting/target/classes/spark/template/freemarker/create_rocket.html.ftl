<#-- @ftlvariable name="description" type="java.lang.String" -->
<#-- @ftlvariable name="location" type="java.lang.String" -->
<#-- @ftlvariable name="timeValue" type="java.lang.String" -->
<#-- @ftlvariable name="missionName" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->


<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - Create Rocket">
</head>

<body>

<div id="title_pane">
    <h3>Rocket Details Page</h3>
</div>

<p>${errorMsg!""}</p>

<div>
    <p>* Fields are required.</p>
</div>
<form name="create_event" action="/missions/create" method="POST">
    <div id="admin_left_pane" class="fieldset_without_border">
        <div><p>Mission Details</p></div>
        <ol>
            <li>
                <label for="name" class="bold">Rocket Name:*</label>
                <input id="name" name="name" type="text" value="${missionName!""}">
            </li>
            <li>
               <label for="time" class="bold">Date and time (dd/mm/yyyy, HH AM/PM):*</label>
               <input id="time" name="time" type="text" value="${time}">
            </li>
            <li>
                <label for="country" class="bold">Country:*</label>
                <input id="country" name="country" type="text" value="${location!""}">
            </li>
            <li>
                <label for="description" class="bold">Description:</label>
                <input id="description" name="description" type="text" value="${description!""}">
            </li>
        </ol>
    </div>

    <#if errorMsg?? && errorMsg?has_content>
        <div id="error">
            <p>Error: ${errorMsg}</p>
        </div>
    </#if>
    <div id="buttonwrapper">
        <button type="submit">Create New Mission</button>
        <a href="/">Cancel</a>
    </div>
</form>

</body>
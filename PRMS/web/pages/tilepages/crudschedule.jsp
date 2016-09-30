<%-- 
    Document   : crudschedule
    Created on : 2016-9-7, 17:40:35
    Author     : linby
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <link href="<c:url value='/css/main.css'/>" rel="stylesheet" type="text/css"/>
        <fmt:setBundle basename="ApplicationResources" />
        <title> <fmt:message key="title.crudschedule"/> </title>
    </head>
    <body>
        <h1><fmt:message key="label.crudschedule"/></h1>
        <c:url var="url" scope="page" value="/nocturne/addeditschedule">
            <c:param name="insert" value="true"/>
        </c:url>
        <a href="${url}" style="float: left"><fmt:message key="label.crudschedule.add"/></a>
        <c:url var="openyearurl" scope="page" value="/nocturne/manageyear">
        </c:url>
        <a href="${openyearurl}" style="float: right"><fmt:message key="label.crudschedule.manageyear"/></a>
        <br/><br/>
        <table class="borderAll">
            <tr>
                <th><fmt:message key="label.crudschedule.dateofprogram"/></th>
                <th><fmt:message key="label.crudschedule.startTime"/></th>
                <th><fmt:message key="label.crudschedule.duration"/></th>
                <th><fmt:message key="label.crudschedule.endTime"/></th>
                <th><fmt:message key="label.crudschedule.name"/></th>
                <th><fmt:message key="label.crudschedule.presenter"/></th>
                <th><fmt:message key="label.crudschedule.producer"/></th>
                <th><fmt:message key="label.crudschedule.edit"/> <fmt:message key="label.crudschedule.delete"/></th>
            </tr>
            <c:forEach var="crudSchedule" items="${scheduleList}" varStatus="status">
                <tr class="${status.index%2==0?'even':'odd'}">
                    <td class="nowrap">${crudSchedule.getFormattedStartDate()}</td>
                    <td class="nowrap">${crudSchedule.getFormattedStartTime()}</td>
                    <td class="nowrap">${crudSchedule.getRadioProgram().getTypicalDuration()}</td>
                    <td class="nowrap">${crudSchedule.getFormattedEndTime()}</td>
                    <td class="nowrap">${crudSchedule.getRadioProgram().getName()}</td>
                    <td class="nowrap">${crudSchedule.presenter.getId()}</td>
                    <td class="nowrap">${crudSchedule.producer.getId()}</td>
                    <td class="nowrap">
                        <c:url var="updurl" scope="page" value="/nocturne/addeditschedule">
                            <c:param name="insert" value="false"/>
                        </c:url>
                        <a href="${updurl}"><fmt:message key="label.crudschedule.edit"/></a>
                        &nbsp;&nbsp;&nbsp;
                        <c:url var="delurl" scope="page" value="/nocturne/deleteschedule">
                        </c:url>
                        <a href="${delurl}"><fmt:message key="label.crudschedule.delete"/></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>

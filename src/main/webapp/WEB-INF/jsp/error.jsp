<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<cheapy:layout pageName="Acceso Denegado">
	<h2 class="titulo" style="font-family: 'Lobster'; font-size: 30px; padding:30px"><em>La página a la que está intentando acceder no existe o no se encuentra disponible.</em></h2>
    
    <spring:url value="/resources/images/Logo Cheapy.png" htmlEscape="true" var="cheapyImage"/>
    <img class="img-responsive" src="${fn:escapeXml(cheapyImage)}"/>

    <p>${fn:escapeXml(exception.message)}</p>
	<button id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster';">Volver</button>
</cheapy:layout>

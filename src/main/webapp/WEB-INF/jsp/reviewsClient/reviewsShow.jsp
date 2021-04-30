<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="reviewClient">

    <h2 class="titulo" style="font-family: 'Lobster'; text-align:center; font-size:200%; padding:10px"><fmt:message key="review"/></h2>

    <form:form modelAttribute="review" class="form-horizontal" id="add-review-form">


	<div class="table-responsive">
    <table class="table table-striped" id="review-table">
        <tr>
            <th><fmt:message key="service"/></th>
            <td><cheapy:showStars value='${review.service}'></cheapy:showStars></td>
        </tr>
        <tr>
            <th><fmt:message key="food"/></th>
            <td><cheapy:showStars value='${review.food}'></cheapy:showStars></td>
        </tr>
        <tr>
            <th><fmt:message key="qualityPrice"/></th>
            <td><cheapy:showStars value='${review.qualityPrice}'></cheapy:showStars></td>
        </tr>
        <tr>
        
            <th><fmt:message key="opinion"/></th>
            <td><c:out value="${review.opinion}"/></td>
        </tr>
    
    </table>
    </div>
    
    </form:form>

	<sec:authorize access="isAuthenticated()">
	<sec:authentication var="principal" property="principal"  />
	<div class="btns-edit">
		<c:if test="${principal.username eq review.escritor.username}">
	    	<spring:url value="{reviewId}/edit" var="editUrl">
		    <spring:param name="reviewId" value="${review.id}"/>
		    </spring:url>
		    
			<button type="button" role="link" onclick="window.location='${fn:escapeXml(editUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	        <span class="glyphicon glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
		        Editar reseña</button>
    	</c:if>
    	
    </div>
    </sec:authorize>
  
</cheapy:layout>

<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

<cheapy:layout pageName="speedOffer">
	<script>
		$(document).ready(function(){
		  $('[data-toggle="desplegable"]').popover();   
		});
	</script>

	<h2 class="titulo" style="font-family: 'Lobster'; text-align:left; font-size:200%; padding:10px; margin-bottom:20px;"><fmt:message key="speedOffer" />
		<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir en menos de alguno de los tres posibles tiempos">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
	</h2>

    <table class="table table-striped" id="speedOfferTable">
    	<tr>
            <th><fmt:message key="client"/></th>
            <td><c:out value="${speedOffer.client.name}"/> </td>
        </tr>
        <tr>
            <th><fmt:message key="offerBeginning"/></th>
            <td><c:out value="${localDateTimeFormat.format(speedOffer.start)}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="offerEnding"/></th>
            <td><c:out value="${localDateTimeFormat.format(speedOffer.end)}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="goldGoal"/>
            <i class="fas fa-bullseye" id="goldGD"></i>
            </th>
            <td><c:out value="${speedOffer.gold.getHour()*60+speedOffer.gold.getMinute()} minutos y"/><c:out value=" ${speedOffer.gold.getSecond()} segundos"/></td>
        </tr>
        <tr>
            <th><fmt:message key="goldDiscount"/>
            <i class="fas fa-medal" id="goldGD"></i>
            </th>
            <td><c:out value="${speedOffer.discountGold}%"/></td>
        </tr>
        <tr>
            <th><fmt:message key="silverGoal"/>
            <i class="fas fa-bullseye" id="silverGD"></i>
            </th>
            <td><c:out value="${speedOffer.silver.getHour()*60+speedOffer.silver.getMinute()} minutos y"/><c:out value=" ${speedOffer.silver.getSecond()} segundos"/></td>
        </tr>
        <tr>
            <th><fmt:message key="silverDiscount"/>
            <i class="fas fa-medal" id="silverGD"></i>
            </th>
            <td><c:out value="${speedOffer.discountSilver}%"/></td>
        </tr>
        <tr>
            <th><fmt:message key="bronzeGoal"/>
            <i class="fas fa-bullseye" id="bronzeGD"></i>
            </th>
            <td><c:out value="${speedOffer.bronze.getHour()*60+speedOffer.bronze.getMinute()} minutos y"/><c:out value=" ${speedOffer.bronze.getSecond()} segundos"/></td>
        </tr>
        <tr>
            <th><fmt:message key="bronzeDiscount"/>
            <i class="fas fa-medal" id="bronzeGD"></i>
            </th>
            <td><c:out value="${speedOffer.discountBronze}%"/></td>
        </tr>
        <tr>
            <th><fmt:message key="municipio"/></th>
            <td><c:out value="${speedOffer.client.municipio}"/></td>
        </tr>
        <sec:authorize access="isAuthenticated()">
        <tr>
            <th><fmt:message key="offerCode"/></th>
            <td><b><c:out value="${speedOffer.code}"/></b></td>
        </tr>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
        <tr>
            <th><fmt:message key="offerCode"/></th>
            <td><b>Para acceder al código debe iniciar sesión</b></td>
        </tr>
        </sec:authorize>
    </table>

    <div class="btn-menu">
	    
	<sec:authorize access="hasAnyAuthority('client')">
	<sec:authentication var="principal" property="principal" />
		<div class="btns-edit">
		<button  type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster'; font-size: 23.5px;">Volver</button>
		<c:if test="${ principal.username eq speedOffer.client.usuar.username}">
			<c:if test="${speedOffer.status eq 'active' || speedOffer.status eq 'hidden' }">
			    <spring:url value="{speedOfferId}/edit" var="editUrl">
			    <spring:param name="speedOfferId" value="${speedOffer.id}"/>
			    </spring:url>
			    <button type="button" role="link" onclick="window.location='${fn:escapeXml(editUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
		        Editar oferta</button>
			</c:if>

			<c:if test="${speedOffer.status eq 'hidden' }">
		        <spring:url value="{speedOfferId}/activate" var="activateUrl">
		        <spring:param name="speedOfferId" value="${speedOffer.id}"/>
		        </spring:url>
		        <button type="button" role="link" onclick="window.location='${fn:escapeXml(activateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
			        Activar oferta</button>
			</c:if>
			
			<c:if test="${speedOffer.status eq 'active' }">
			    <spring:url value="{speedOfferId}/disable" var="deactivateUrl">
			    <spring:param name="speedOfferId" value="${speedOffer.id}"/>
			    </spring:url>
			    <button type="button" role="link" onclick="window.location='${fn:escapeXml(deactivateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	            <span class="glyphicon glyphicon glyphicon-trash" aria-hidden="true" style="padding: 5px"> </span>
		        Desactivar oferta</button>
	        </c:if>
	    </c:if>
	    </div>
	    </sec:authorize>
    </div>
	
	

</cheapy:layout>

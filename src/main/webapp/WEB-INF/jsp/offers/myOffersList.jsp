<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<cheapy:layout pageName="ofertasM">
	<script>
		$(document).ready(function(){
		  $('[data-toggle="desplegable"]').popover();   
		});
	</script>
	<script type="text/javascript">
		
	</script>
	<div class="text-center">
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px"><fmt:message key="foodOffers"/>
    	<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir el plato indicado en la oferta">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
    </h2>
	<c:if test="${empty foodOfferLs }">
		<p id="vacio" >No hay ninguna oferta por plato específico creada.</p>
		
		<div class="btn-home-max">
			<div class="btn-home">
	            <button type="button" role="link" onclick="window.location='/offers/food/new'" style="font-family: 'Lobster'; font-size: 20px;margin:5px;text-align:center;"  class="btn-block">
	            <span class="glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
	            <fmt:message key="createOffers"/> </button>
            </div>
        </div>
	</c:if>
	<c:if test="${not empty foodOfferLs }">
		<div class="table-responsive">
		    <table id="foodOfferTable" class="table table-striped">
		        <thead>
		        <tr>
		        	<th><fmt:message key="food"/></th>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th><fmt:message key="status"/></th>
		            <th><fmt:message key="municipio"/></th>
		            <th> <spring:url value="/offers/food/new" var="newFoodUrl">
		    </spring:url>
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${foodOfferLs}" var="foodOffer">
		            <tr>
		                <td>
		                    <c:out value="${foodOffer.food}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(foodOffer.start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(foodOffer.end)}"/>
		                </td>
		                <td>
		                	<c:if test="${foodOffer.status == 'active'}">
		                    	<c:out value="Activa"/>
		                    </c:if>	
		                    <c:if test="${foodOffer.status == 'hidden'}">
		                    	<c:out value="Oculta"/>
		                    </c:if>	
		                    <c:if test="${foodOffer.status == 'inactive'}">
		                    	<c:out value="Inactiva"/>
		                    </c:if>	
		                </td>
		                <td>
		                    <c:out value="${foodOffer.client.municipio}"/>
		                </td>
		                <td>
			                <spring:url value="/offers/food/{foodOfferId}" var="foodOfferUrl">
			                        <spring:param name="foodOfferId" value="${foodOffer.id}"/>
			                </spring:url>
			                <div class="btn-detalles">
		                		<button type="button" role="link" onclick="window.location='${fn:escapeXml(foodOfferUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		                		<span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
			                	<fmt:message key="details"/></button>
		            		</div>
		                </td> 
		                  
		            </tr>
		        </c:forEach>
		        </tbody>
		    </table>
		</div>
    </c:if>
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px"><fmt:message key="nuOffers"/>
    	<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir con más comensales que alguno de los tres posibles objetivos">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
    </h2>
	<c:if test="${empty nuOfferLs }">
		<p id="vacio" >No hay ninguna oferta por número de comensales creada.</p>
		<div class="btn-home-max">
			<div class="btn-home">
	            <button type="button" role="link" onclick="window.location='/offers/nu/new'" style="font-family: 'Lobster'; font-size: 20px;margin:5px;" class="btn-block">
	            <span class="glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
	            <fmt:message key="createOffers"/> </button>
            </div>
        </div>
	</c:if>
	<c:if test="${not empty nuOfferLs }">
		<div class="table-responsive">
		    <table id="nuOfferTable" class="table table-striped">
		        <thead>
		        <tr>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th><fmt:message key="status"/></th>
		            <th><fmt:message key="municipio"/></th>
		            <th> <spring:url value="/offers/nu/new" var="newNuUrl">
		    </spring:url>
		            
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${nuOfferLs}" var="nuOffer">
		            <tr>
		                
		                <td>
		                    <c:out value="${localDateTimeFormat.format(nuOffer.start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(nuOffer.end)}"/>
		                </td>
		                <td>
		                	<c:if test="${nuOffer.status == 'active'}">
		                    	<c:out value="Activa"/>
		                    </c:if>	
		                    <c:if test="${nuOffer.status == 'hidden'}">
		                    	<c:out value="Oculta"/>
		                    </c:if>	
		                    <c:if test="${nuOffer.status == 'inactive'}">
		                    	<c:out value="Inactiva"/>
		                    </c:if>	
		                </td>
		                <td>
		                    <c:out value="${nuOffer.client.municipio}"/>
		                </td>
		                <td>
			                <spring:url value="/offers/nu/{nuOfferId}" var="nuOfferUrl">
			                        <spring:param name="nuOfferId" value="${nuOffer.id}"/>
			                </spring:url>
			                <div class="btn-detalles">
				                <button type="button" role="link" onclick="window.location='${fn:escapeXml(nuOfferUrl)}'" class="btn-detalles" style="font-family: 'Lobster'; font-size: 20px;">
				                <span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
				                <fmt:message key="details"/> </button>
				            </div>
		                </td>  
		            </tr>
		        </c:forEach>
		        </tbody>
		    </table>
    	</div>
    </c:if>
    
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px"><fmt:message key="speedOffers"/>
    	<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir en menos de alguno de los tres posibles tiempos">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
    </h2>
	<c:if test="${empty speedOfferLs }">
		<p id="vacio" >No hay ninguna oferta por tiempo empleado en comer creada.</p>
		<div class="btn-home-max">
			<div class="btn-home">
	            <button type="button" role="link" onclick="window.location='/offers/speed/new'" style="font-family: 'Lobster'; font-size: 20px;margin:5px;" class="btn-block">
	            <span class="glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
	            <fmt:message key="createOffers"/> </button>
        	</div>
        </div>
	</c:if>
	<c:if test="${not empty speedOfferLs }">
		<div class="table-responsive">
		    <table id="speedOfferTable" class="table table-striped">
		        <thead>
		        <tr>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th><fmt:message key="status"/></th>
		            <th><fmt:message key="municipio"/></th>
		            <th> <spring:url value="/offers/speed/new" var="newSpeedUrl">
		    </spring:url>
		            
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${speedOfferLs}" var="speedOffer">
		            <tr>
		                
		                <td>
		                    <c:out value="${localDateTimeFormat.format(speedOffer.start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(speedOffer.end)}"/>
		                </td>
		                <td>
		                	<c:if test="${speedOffer.status == 'active'}">
		                    	<c:out value="Activa"/>
		                    </c:if>	
		                    <c:if test="${speedOffer.status == 'hidden'}">
		                    	<c:out value="Oculta"/>
		                    </c:if>	
		                    <c:if test="${speedOffer.status == 'inactive'}">
		                    	<c:out value="Inactiva"/>
		                    </c:if>	
		                </td>
		                <td>
		                    <c:out value="${speedOffer.client.municipio}"/>
		                </td>
		                <td>
		                    <spring:url value="/offers/speed/{speedOfferId}" var="speedOfferUrl">
		                        <spring:param name="speedOfferId" value="${speedOffer.id}"/>
		                    </spring:url>
		                    <div class="btn-detalles">
			                    <button type="button" role="link" onclick="window.location='${fn:escapeXml(speedOfferUrl)}'" class="btn-detalles" style="font-family: 'Lobster'; font-size: 20px;">
			                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
			                    <fmt:message key="details"/> </button>
			                </div>
		                </td>
		                  
		            </tr>
		        </c:forEach>
		        </tbody>
		    </table>
    	</div>
    </c:if>
    
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px"><fmt:message key="timeOffers"/>
    	<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir en el local durante el intervalo indicado">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
    </h2>
	<c:if test="${empty timeOfferLs }">
		<p id="vacio" >No hay ninguna oferta por franja horaria creada.</p>
		<div class="btn-home-max">
			<div class="btn-home">
	            <button type="button" role="link" onclick="window.location='/offers/time/new'" style="font-family: 'Lobster'; font-size: 20px;margin:5px;" class="btn-block">
	            <span class="glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
	            <fmt:message key="createOffers"/> </button>
	        </div>
        </div>
	</c:if>
	<c:if test="${not empty timeOfferLs }">
		<div class="table-responsive">
		    <table id="timeOfferTable" class="table table-striped">
		        <thead>
		        <tr>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th><fmt:message key="status"/></th>
		            <th><fmt:message key="municipio"/></th>
		            <th><spring:url value="/offers/time/new" var="newTimeUrl">
		    </spring:url>
		        </tr>
		        </thead>
		        <tbody>
		        	<c:forEach items="${timeOfferLs}" var="timeOffer">
		            <tr>
		                
		                <td>
		                    <c:out value="${localDateTimeFormat.format(timeOffer.start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(timeOffer.end)}"/>
		                </td>
		                <td>
		                	<c:if test="${timeOffer.status == 'active'}">
		                    	<c:out value="Activa"/>
		                    </c:if>	
		                    <c:if test="${timeOffer.status == 'hidden'}">
		                    	<c:out value="Oculta"/>
		                    </c:if>	
		                    <c:if test="${timeOffer.status == 'inactive'}">
		                    	<c:out value="Inactiva"/>
		                    </c:if>	
		                </td>
		                <td>
		                    <c:out value="${timeOffer.client.municipio}"/>
		                </td>
		                <td>
		                	<spring:url value="/offers/time/{timeOfferId}" var="timeOfferUrl">
		                        <spring:param name="timeOfferId" value="${timeOffer.id}"/>
		                    </spring:url>
		                    <div class="btn-detalles">
			                    <button type="button" role="link" onclick="window.location='${fn:escapeXml(timeOfferUrl)}'" class="btn-detalles" style="font-family: 'Lobster'; font-size: 20px;">
			                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
			                    <fmt:message key="details"/> </button>
			                </div>
		                </td> 
		            </tr>
		        </c:forEach>
		        </tbody>
		    </table>
    	</div>
    </c:if>
    </div>
</cheapy:layout>

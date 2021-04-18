<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="ofertas">
	<div class="text-center">
		<div class="btn-filter-max">
			<spring:url value="/offers/foodOfferList/{page}" var="foodOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    
		    
			<button type="button" class="btn-filter" role="link" onclick="window.location='${fn:escapeXml(foodOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de plato específico</button>
			
			<spring:url value="/offers/nuOfferList/{page}" var="nuOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    
			<button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(nuOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas por número de comensales</button>
			
			
			<spring:url value="/offers/speedOfferList/{page}" var="speedOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de velocidad</button>
			
			<spring:url value="/offers/timeOfferList/{page}" var="timeOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(timeOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de franja horaria</button>
		</div>
	</div>		
	
	<spring:url value="/offersByName/{page}" var="SearchNameOfferListUrl">
				<spring:param name="page" value="0"/>
	</spring:url>
	<form class="example" action="${fn:escapeXml(SearchNameOfferListUrl)}">
		<h2 class="text-center" style="font-family: 'Lobster'; text-align:center; font-size:150%;  color: rgb(0, 64, 128); padding:10px;" >Búsqueda por nombre del bar/restaurante: </h2>
		<div class="text-center">
	  		<input type="text" placeholder="Búsqueda por nombre" name="name" style="border:solid; border-color: rgb(0, 64, 128);">
	  		<button type="submit" class="btn-search"><i class="fa fa-search"></i>
	  		<span class="glyphicon 	glyphicon glyphicon-search" aria-hidden="true" style="padding: 5px"> </span>
	  		</button>
  		</div>
	</form>
	
	<spring:url value="/offersByFood/{page}" var="SearchFoodOfferListUrl">
				<spring:param name="page" value="0"/>
	</spring:url>
	<form class="example" action="${fn:escapeXml(SearchFoodOfferListUrl)}">
		<h2 class="text-center" style="font-family: 'Lobster'; text-align:center; font-size:150%;  color: rgb(0, 64, 128); padding:10px;" >Búsqueda por tipo de comida: </h2>
		<div class="text-center">
	  		<input type="text" placeholder="Búsqueda por tipo comida (Ej: americana)" name="name" style="border:solid; border-color: rgb(0, 64, 128);">
	  		<button type="submit" class="btn-search"><i class="fa fa-search"></i>
	  		<span class="glyphicon 	glyphicon glyphicon-search" aria-hidden="true" style="padding: 5px"> </span>
	  		</button>
  		</div>
	</form>
	
	<spring:url value="/offersByPlace/{page}" var="SearchPlaceOfferListUrl">
				<spring:param name="page" value="0"/>
	</spring:url>
	<form class="example" action="${fn:escapeXml(SearchPlaceOfferListUrl)}">
		<div class="text-center">
			<select name="municipio" class="select-municipio" >
			
			<c:forEach items="${municipios}" var="entry">
				<option value="${entry}">${entry.toString()}</option>
			</c:forEach>
			</select>
			<button type="submit" class="btn-mas">Buscar por municipio</button>
		</div>
	</form>

    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px"><fmt:message key="foodOffers"/>:</h2>
    
	<c:if test="${empty foodOfferLs }">
		<p id="vacio" >No hay ninguna oferta por plato específico activa.</p>
	</c:if>
	<c:if test="${not empty foodOfferLs }">

  <div class="table-responsive">
    <table id="foodOfferTable" class="table table-striped">
        <thead>
        <tr>
        	<!-- <th style="width: 150px;">Restaurante</th> -->
        	<th><fmt:message key="name"/></th>
        	<th><fmt:message key="food"/></th>
        	<th><fmt:message key="discount"/></th>
            <th><fmt:message key="startDate"/></th>
            <th><fmt:message key="endDate"/></th>
            <th><fmt:message key="municipio"/></th>
            
            <th> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${foodOfferLs}" var="foodOffer">
            <tr>
            	<td>
                    <a href="/restaurant/${foodOffer.client.id}"><c:out value="${foodOffer.client.name}"/></a>
                </td>
                <td>
                    <c:out value="${foodOffer.food}"/>
                </td>
                <td>
                    <c:out value="${foodOffer.discount}%"/>
                </td>
                <td>
                    <c:out value="${localDateTimeFormat.format(foodOffer.start)}"/>
                </td>
                <td>
                    <c:out value="${localDateTimeFormat.format(foodOffer.end)}"/>
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
    <div class="text-center">
	    <button type="button" class="btn-mas" role="link" onclick="window.location='${fn:escapeXml(foodOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		<span class="glyphicon 	glyphicon glyphicon-plus" aria-hidden="true" style="padding: 5px"> </span>
		Ver más</button>
	</div>
    </div>
    
    </c:if>
    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px"><fmt:message key="nuOffers"/>:</h2>
	<c:if test="${empty nuOfferLs }">
		<p id="vacio" >No hay ninguna oferta por número de comensales activa.</p>
	</c:if>
	<c:if test="${not empty nuOfferLs }">

 	<div class="table-responsive">        
    <table id="nuOfferTable" class="table table-striped">
        <thead>
        <tr>
        	<!-- <th style="width: 150px;">Restaurante</th> -->
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="startDate"/></th>
            <th><fmt:message key="endDate"/></th>
            <th><fmt:message key="goldGoal"/></th>
            <th><fmt:message key="goldDiscount"/></th>
            <th><fmt:message key="municipio"/></th>
            <th> </th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${nuOfferLs}" var="nuOffer">
            <tr>
                <td>
                <a href="/restaurant/${nuOffer.client.id}"><c:out value="${nuOffer.client.name}"/></a>
                </td>
                <td>
                    <c:out value="${localDateTimeFormat.format(nuOffer.start)}"/>
                </td>
                <td>
                    <c:out value="${localDateTimeFormat.format(nuOffer.end)}"/>
                </td>
                <td>
                    <c:out value="${nuOffer.gold} comensales"/>
                </td>
                <td>
                    <c:out value="${nuOffer.discountGold}%"/>
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
    <div class="text-center">
	    <button type="button" class="btn-mas" role="link" onclick="window.location='${fn:escapeXml(nuOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		<span class="glyphicon 	glyphicon glyphicon-plus" aria-hidden="true" style="padding: 5px"> </span>
		Ver más</button>
	</div>
    </div>

    </c:if>
    
    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px"><fmt:message key="speedOffers"/>:</h2>
	<c:if test="${empty speedOfferLs }">
		<p id="vacio" >No hay ninguna oferta por tiempo empleado en comer activa.</p>
	</c:if>
	<c:if test="${not empty speedOfferLs }">
    
		<div class="table-responsive">
		    <table id="speedOfferTable" class="table table-striped">
		        <thead>
		        <tr>
		        	<!-- <th style="width: 150px;">Restaurante</th> -->
		            <th><fmt:message key="name"/></th>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th><fmt:message key="goldGoal"/></th>
		            <th><fmt:message key="goldDiscount"/></th>
		            <th><fmt:message key="municipio"/></th>
		            <th> </th>
		            
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${speedOfferLs}" var="speedOffer">
		            <tr>
		                <td>
                	      <a href="/restaurant/${speedOffer.client.id}"><c:out value="${speedOffer.client.name}"/></a>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(speedOffer.start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(speedOffer.end)}"/>
		                </td>
		                <td>
		                    <c:out value="${speedOffer.gold} minutos"/>
		                </td>
		                <td>
		                    <c:out value="${speedOffer.discountGold}%"/>
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
		    <div class="text-center">
			  	<spring:url value="/offers/speedOfferList" var="speedOfferUrl"></spring:url>
	   			<button type="button" class="btn-mas" role="link" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-plus" aria-hidden="true" style="padding: 5px"> </span>
				Ver más</button>
			</div>
	    </div>

    </c:if>
    
    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px"><fmt:message key="timeOffers"/>:</h2>
	<c:if test="${empty timeOfferLs }">
		<p id="vacio" >No hay ninguna oferta por franja horaria activa.</p>
	</c:if>
	<c:if test="${not empty timeOfferLs }">
    
		<div class="table-responsive">
		    <table id="timeOfferTable" class="table table-striped">
		        <thead>
		        <tr>
		        	<!-- <th style="width: 150px;">Restaurante</th> -->
		            <th><fmt:message key="name"/></th>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th><fmt:message key="init"/></th>
		            <th><fmt:message key="finishOffer"/></th>
		            <th><fmt:message key="municipio"/></th>
		            <th> </th>
		        </tr>
		        </thead>
		        <tbody>
		        	<c:forEach items="${timeOfferLs}" var="timeOffer">
		            <tr>
		                <td>
                        <a href="/restaurant/${timeOffer.client.id}"><c:out value="${timeOffer.client.name}"/></a>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(timeOffer.start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(timeOffer.end)}"/>
		                </td>
		                <td>
		                    <c:out value="${timeOffer.init}h"/>
		                </td>
		                <td>
		                    <c:out value="${timeOffer.finish}h"/>
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
		    </table>
		    <div class="text-center">
			    <spring:url value="/offers/timeOfferList" var="timeOfferUrl"></spring:url>
	    		<button type="button" class="btn-mas" role="link" onclick="window.location='${fn:escapeXml(timeOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-plus" aria-hidden="true" style="padding: 5px"> </span>
				Ver más</button>
			</div>
  	  </div>
    </c:if>
  
	
</cheapy:layout>

<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<style>


</style>

<cheapy:layout pageName="registro">
	<script>
		function pagNum(pagina) {
			paginaAct = pagina + 1;
			document.write("Página " + paginaAct + " <br />");
		}
	
	</script>

    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px">Historial de Ofertas</h2>
	<c:if test="${empty datos }">
		<p id="vacio" >No hay ninguna oferta creada.</p>
	</c:if>
	<c:if test="${not empty datos }">
		<div class="table-responsive">
		    <table id="offerTable" class="table table-striped">
		        <thead>
		        <tr>
		        	<th>Restaurante</th>
		        	<th>ID</th>
		        	<th>Tipo de oferta</th>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th><fmt:message key="status"/></th>
		            <th></th>
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${datos}" var="datos">
		            <tr>
		                <td>
		                    <a href="/restaurant/${fn:escapeXml(datos[0].client.id)}"><c:out value="${datos[0].client.name}"/></a>
		                </td>
		                <td>
		                    <c:out value="${datos[0].id}"/>
		                </td>
		                <td>
		                	<c:if test="${datos[1] == 'time'}">
		                    	<c:out value="Por franja horaria"/>
		                    </c:if>
		                    <c:if test="${datos[1] == 'nu'}">
		                    	<c:out value="Por número de comensales"/>
		                    </c:if>
		                    <c:if test="${datos[1] == 'speed'}">
		                    	<c:out value="Por velocidad"/>
		                    </c:if>
		                    <c:if test="${datos[1] == 'food'}">
		                    	<c:out value="Por plato específico"/>
		                    </c:if>
		                </td>
		                
		                <td>
		                    <c:out value="${localDateTimeFormat.format(datos[0].start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(datos[0].end)}"/>
		                </td>
		                <td>
		                	<c:if test="${datos[0].status == 'active'}">
		                    	<c:out value="Activa"/>
		                    </c:if>	
		                    <c:if test="${datos[0].status == 'hidden'}">
		                    	<c:out value="Oculta"/>
		                    </c:if>	
		                    <c:if test="${datos[0].status == 'inactive'}">
		                    	<c:out value="Inactiva"/>
		                    </c:if>		                 
		                </td>
		                
		                <td>
	                	<spring:url value="/administrators/offers/${datos[1]}/${datos[0].id}" var="offerUrl">
	                	</spring:url>
	               		<div class="btn-detalles">
                			<button type="button" role="link" onclick="window.location='${fn:escapeXml(offerUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
                			<span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
	                		<fmt:message key="details"/></button>
            			</div>
                		</td>
		                
		            </tr>
		        </c:forEach>
		        </tbody>
		    </table>
	</div>
	<div class="text-center">
    	<script type="text/javascript">
			          
    		pagNum(${page});
								
		</script>
    </div>
	<div class="row-pag-btn">
	    <div class="column-pag-btn" style="text-align: left;">
	    	<c:if test='${page!=0}'>
	
		    	<spring:url value="/administrators/offersRecord{page}" var="SearchOfferListUrl">
		    		<spring:param name="page" value="${page-1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(SearchOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-arrow-left" aria-hidden="true" style="padding: 5px"> </span>
				Pág. anterior</button>

   			</c:if>
	    	​
	    </div>
	    <div class="column-pag-btn" style="text-align: right;">
	    	<c:if test="${nextPage > 0}">

		    	<spring:url value="/administrators/offersRecord{page}" var="SearchOfferListUrl">
		    		<spring:param name="page" value="${page+1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag"  role="link" onclick="window.location='${fn:escapeXml(SearchOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-arrow-right" aria-hidden="true" style="padding: 5px"> </span>
				Pág. siguiente</button>

			</c:if>​
		</div>
	</div>
		
    </c:if>
</cheapy:layout>

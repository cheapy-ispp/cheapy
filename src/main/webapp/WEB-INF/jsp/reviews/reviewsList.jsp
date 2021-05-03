<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="reviews">
	<script>
		function pagNum(pagina) {
			paginaAct = pagina + 1;
			document.write("Página " + paginaAct + " <br />");
		}
	</script>
    <div class="text-center">
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%;   padding:10px"><fmt:message key="reviewsCheapy"/></h2>
	<div class="table-responsive">
	    <table id="reviewTable" class="table table-striped">
	        <thead>
		        <tr>
		        	<th><fmt:message key="user"/></th>
		        	<th><fmt:message key="stars"/></th>
		            <th><fmt:message key="opinion"/></th>
		            <th> </th>
		        </tr>
	        </thead>
	        <tbody>
	        <c:choose>
		        <c:when test="${empty reviewsLs}">
		        	<tr><td colspan="4"><em><c:out value="No se ha realizado ninguna valoración por el momento."/></em></td></tr>
		        </c:when>
		        <c:otherwise>
		        <c:forEach items="${reviewsLs}" var="review">
		       
		            <tr>
		                <td>
		                    <c:out value="${review.escritor.username}"/>
		                </td>
		                <td>
		                    <div style="display: inline-block; margin: auto;">
		                    	<cheapy:showStars value='${review.stars}'></cheapy:showStars>
							</div>
		                </td>
		                <td>
		                    <c:out value="${review.opinion}"/>
		                </td>
		                <td>
			                <spring:url value="/reviews/{reviewId}" var="reviewUrl">
			                        <spring:param name="reviewId" value="${review.id}"/>
			                </spring:url>
			                <div class="btn-detalles">
		                		<button type="button" role="link" onclick="window.location='${fn:escapeXml(reviewUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		                		<span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
			                	<fmt:message key="details"/></button>
		            		</div>
		                </td> 
		                  
		            </tr>
		           
		        </c:forEach>
		        </c:otherwise>
	        </c:choose>
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
			    	<spring:url value="/reviewsList/{page}" var="reviewsListUrl">
			    		<spring:param name="page" value="${page-1}"/>
			    	</spring:url>
			    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(reviewsListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
					<span class="glyphicon 	glyphicon glyphicon-arrow-left" aria-hidden="true" style="padding: 5px"> </span>
					Pág. anterior</button>
		    </c:if>
	    	​
	    </div>
	    <div class="column-pag-btn" style="text-align: right;">
	    	<c:if test="${nextPage > 0}">
		    	<spring:url value="/reviewsList/{page}" var="reviewsListUrl">
		    		<spring:param name="page" value="${page+1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(reviewsListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				Pág. siguiente
				<span class="glyphicon 	glyphicon glyphicon-arrow-right" aria-hidden="true" style="padding: 5px"> </span></button>
		</c:if>​
		</div>
	</div>
	</div>
</cheapy:layout>

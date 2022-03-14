<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <jsp:include page="../../fragments/headTag.jsp" />

    <!-- datatables-->
    <spring:url value="/resources/css/dataTables.bootstrap4.min.css" var="bdat4" />
    <link rel="stylesheet" href="${bdat4}" type="text/css"/>

    <spring:url value="/resources/css/responsive.bootstrap4.min.css" var="bdrespat4" />
    <link rel="stylesheet" href="${bdrespat4}" type="text/css"/>

    <spring:url value="/resources/css/dtresponsive/buttons/buttons.bootstrap.min.css" var="dtbuttons" />
    <link rel="stylesheet" href="${dtbuttons}" type="text/css"/>
    <!-- fin datables-->
    <!-- DATE PICKER -->
    <spring:url value="/resources/css/datepicker.css" var="datepickerCss" />
    <link href="${datepickerCss}" rel="stylesheet" type="text/css"/>
    <!-- END DATE PICKER -->
    <spring:url value="/resources/css/sweetalert2/sweetalert2.min.css" var="sweetalert2" />
    <link rel="stylesheet" href="${sweetalert2}">

    <style>
        a.buttons-collection {
            margin-left: 1em;
        }

    </style>
</head>
<body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
<jsp:include page="../../fragments/bodyHeader.jsp" />
<div class="app-body">
    <jsp:include page="../../fragments/sideBar.jsp" />
    <!-- Main content -->
    <div class="main">
        <!-- Breadcrumb -->
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a>
                <i class="fa fa-angle-right"></i> <a href="<spring:url value="/super/casacaso/" htmlEscape="true "/>"><spring:message code="intensiveMonitoring" /></a>
            </li>
        </ol>
        <c:set var="exportar"><spring:message code="export" /></c:set>
        <c:set var="confirmar"><spring:message code="confirm" /></c:set>
        <c:set var="cerrarCaso"><spring:message code="close.case" /></c:set>
        <c:set var="deshabilitar"><spring:message code="disable" /></c:set>
        <c:set var="recordDisabledLabel"><spring:message code="recordDisabled" /></c:set>
        <c:set var="successLabel"><spring:message code="process.success" /></c:set>
        <c:set var="seleccionarCaso"><spring:message code="select.case" /></c:set>
        <c:set var="casoInactivo"><spring:message code="inactive.case" /></c:set>

        <spring:url value="/super/particaso/getParticipantsCasos" var="getCodesUrl"/>
        <spring:url value="/super/casacaso/closeCase" var="closeUrl"/>
        <div class="container-fluid">
            <div class="card">
                <div class="card-header">
                    <i class="fa fa-list-alt"></i> <spring:message code="intensiveMonitoring" />
                </div>
                <div class="card-block">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="btn-group">
                                <spring:url value="/super/casacaso/newCase"	var="newCase"/>
                                <button id="new_version" onclick="location.href='${fn:escapeXml(newCase)}'" class="btn btn-success">
                                    <spring:message code="add" /> <i class="fa fa-plus"></i>
                                </button>
                            </div>
                            <br>
                            <br>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered" id="lista_casos" style="width:100%">
                            <thead>
                            <tr>
                                <th width="10%"><spring:message code="house" /></th>
                                <th width="13%"><spring:message code="logindate" /></th>
                                <th width="12%"><spring:message code="positive" /></th>
                                <th width="12%"><spring:message code="lbl.positive.by" /></th>
                                <th width="13%"><spring:message code="FIS" /></th>
                                <th width="13%"><spring:message code="fif" /></th>
                                <th width="13%"><spring:message code="logoutdate" /></th>
                                <th width="14%"><spring:message code="actions" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${casos}" var="parti">
                                <spring:url value="/super/casacaso/editCase/{codigo}"
                                            var="editUrl">
                                    <spring:param name="codigo" value="${parti.codigoCasoParticipante}" />
                                </spring:url>
                                <spring:url value="/super/casacaso/participants/{codigo}"
                                            var="participantsUrl">
                                    <spring:param name="codigo" value="${parti.codigoCaso.codigoCaso}" />
                                </spring:url>
                                <spring:url value="/super/casacaso/actions/disable/{codigo}"
                                            var="disableUrl">
                                    <spring:param name="codigo" value="${parti.codigoCaso.codigoCaso}-${parti.codigoCaso.casa.codigoCHF}" />
                                </spring:url>
                                <tr>
                                    <td><c:out value="${parti.codigoCaso.casa.codigoCHF}" /></td>
                                    <td><fmt:formatDate value="${parti.codigoCaso.fechaInicio}" pattern="dd/MM/yyyy" /></td>
                                    <td><c:out value="${parti.participante.participante.codigo}" /></td>
                                    <td>
                                        <c:forEach items="${positivoPor}" var="cat">
                                            <c:if test="${cat.catKey eq parti.positivoPor}">
                                                <c:out value="${cat.spanish}" />
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td><fmt:formatDate value="${parti.fis}" pattern="dd/MM/yyyy" /></td>
                                    <td><fmt:formatDate value="${parti.fechaEnfermedad}" pattern="dd/MM/yyyy" /></td>
                                    <td><fmt:formatDate value="${parti.codigoCaso.fechaInactiva}" pattern="dd/MM/yyyy" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${parti.codigoCaso.inactiva=='1'}">
                                                <button title="<spring:message code="edit" />" class="btn btn-outline-primary btn-sm" disabled><i class="fa fa-edit"></i></button>
                                                <a title="<spring:message code="participants" />" href="${fn:escapeXml(participantsUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-users"></i></a>
                                                <button title="<spring:message code="close.case" />" class="btn btn-outline-primary btn-sm" disabled><i class="fa fa-sign-out"></i></button>
                                                <button title="<spring:message code="disable" />" class="btn btn-outline-primary btn-sm" disabled><i class="fa fa-trash-o"></i></button>
                                            </c:when>
                                            <c:otherwise>
                                                <a title="<spring:message code="edit" />" href="${fn:escapeXml(editUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-edit"></i></a>
                                                <a title="<spring:message code="participants" />" href="${fn:escapeXml(participantsUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-users"></i></a>
                                                <a title="<spring:message code="close.case" />" data-toggle="modal" data-id="${parti.codigoCaso.codigoCaso}" class="btn btn-outline-primary btn-sm salida"><i class="fa fa-sign-out"></i></a>
                                                <a title="<spring:message code="disable" />" data-toggle="modal" data-id="${fn:escapeXml(disableUrl)}" class="btn btn-outline-primary btn-sm desact"><i class="fa fa-trash-o"></i></a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <br>
                    <br>
                    <br>
                    <!--<div class="row">
                        <div class="col-md-12">
                            <form action="#" autocomplete="off" id="print-form" class="form-horizontal">
                                <div class="form-group row">
                                    <label class="form-control-label col-md-4" for="etiquetas"><spring:message code="label.types" />
                                        <span class="required">*</span>
                                    </label>
                                    <div class="input-group col-md-6">
                                    <span class="input-group-addon">
                                        <i class="fa fa-eyedropper"></i>
                                    </span>
                                        <select class="form-control select2-multiple" multiple id="etiquetas" name="etiquetas">
                                            <c:forEach items="${etiquetas}" var="etiqueta">
                                                <option value="${etiqueta.messageKey}">${etiqueta.spanish}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="form-actions fluid">
                                    <div class="col-md-offset-10 col-md-12">
                                        <button id="btnPrint" type="submit" name="btnPrint" class="btn btn-success">
                                            <spring:message code="print.selected" /> <i class="fa fa-print"></i>
                                        </button>
                                        <br>
                                        <br>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>-->
                </div>
            </div>
            <div class="modal fade" id="basic" tabindex="-1" data-role="basic" data-backdrop="static" data-aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" data-aria-hidden="true"></button>
                            <div id="titulo"></div>
                        </div>
                        <div class="modal-body">
                            <input type=hidden id="accionUrl"/>
                            <div id="cuerpo"></div>
                            <form action="#" autocomplete="off" id="close-form" class="form-horizontal">
                                <div id="dvSalida" class="form-group row">
                                    <label class="form-control-label col-md-3" for="fechaSalida"><spring:message code="logoutdate" />
                                            <span class="required">
                                                 *
                                            </span>
                                    </label>
                                    <div class="input-group col-md-9">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i>
                                                </span>
                                        <input name="fechaSalida" id="fechaSalida" class="form-control date-picker" type="text" data-date-end-date="+0d" value="" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel" /></button>
                            <button type="button" id="btnOkAct" class="btn btn-info" onclick="ejecutarAccion()"><spring:message code="ok" /></button>
                            <button type="button" id="btnOkPrint" class="btn btn-info" onclick="printSelected()"><spring:message code="ok" /></button>
                            <button type="button" id="btnOkClose" class="btn btn-info"><spring:message code="ok" /></button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            </div>
        </div>
        <!-- /.conainer-fluid -->
    </div>

<jsp:include page="../../fragments/bodyFooter.jsp" />
<jsp:include page="../../fragments/corePlugins.jsp" />
<c:choose>
    <c:when test="${cookie.eIcsLang.value == null}">
        <c:set var="lenguaje" value="es"/>
    </c:when>
    <c:otherwise>
        <c:set var="lenguaje" value="${cookie.eIcsLang.value}"/>
    </c:otherwise>
</c:choose>
<!-- GenesisUI main scripts -->
<!-- datatables-->
<spring:url value="/resources/js/libs/dataTableResponsive/jquery.dataTables.min.js" var="TablesResponsive" />
<script type="text/javascript" src="${TablesResponsive}"></script>

<spring:url value="/resources/js/libs/dataTableResponsive/dataTables.bootstrap4.min.js" var="Tablesb4" />
<script type="text/javascript" src="${Tablesb4}"></script>

<spring:url value="/resources/js/libs/dataTableResponsive/dataTables.responsive.min.js" var="TablesResponsive" />
<script type="text/javascript" src="${TablesResponsive}"></script>

<spring:url value="/resources/js/libs/dataTableResponsive/responsive.bootstrap4.min.js" var="TResponsiveb4" />
<script type="text/javascript" src="${TResponsiveb4}"></script>

<spring:url value="/resources/js/libs/dataTableResponsive/buttons/dataTables.buttons.min.js" var="dTButtons" />
<script type="text/javascript" src="${dTButtons}"></script>

<spring:url value="/resources/js/libs/dataTableResponsive/buttons/buttons.bootstrap.min.js" var="buttonsBT" />
<script type="text/javascript" src="${buttonsBT}"></script>

<spring:url value="/resources/js/libs/jszip/jszip.min.js" var="buttonsJszip" />
<script type="text/javascript" src="${buttonsJszip}"></script>

<spring:url value="/resources/js/libs/pdfmake/pdfmake.min.js" var="buttonsPdf" />
<script type="text/javascript" src="${buttonsPdf}"></script>

<spring:url value="/resources/js/libs/pdfmake/vfs_fonts.js" var="pdfFonts" />
<script type="text/javascript" src="${pdfFonts}"></script>

<spring:url value="/resources/js/libs/dataTableResponsive/buttons/buttons.html5.min.js" var="buttonsHtml5" />
<script type="text/javascript" src="${buttonsHtml5}"></script>
<!-- fin datatables-->

<spring:url value="/resources/js/libs/select2.min.js" var="selectJs" />
<script type="text/javascript" src="${selectJs}"></script>
<spring:url value="/resources/js/libs/jquery.validate.js" var="validateJs" />
<script src="${validateJs}" type="text/javascript"></script>
<spring:url value="/resources/js/libs/jquery-validation/localization/messages_{language}.js" var="jQValidationLoc">
    <spring:param name="language" value="${lenguaje}" />
</spring:url>
<script src="${jQValidationLoc}"></script>
<!-- bootstrap datepicker -->
<spring:url value="/resources/js/libs/bootstrap-datepicker/bootstrap-datepicker.js" var="datepickerPlugin" />
<script src="${datepickerPlugin}"></script>
<spring:url value="/resources/js/libs/bootstrap-datepicker/locales/bootstrap-datepicker.{languagedt}.js" var="datePickerLoc">
    <spring:param name="languagedt" value="${lenguaje}" /></spring:url>
<script src="${datePickerLoc}"></script>

<spring:url value="/resources/js/views/handleDatePickers.js" var="handleDatePickers" />
<script src="${handleDatePickers}"></script>
<spring:url value="/resources/js/app.js" var="App" />
<script src="${App}" type="text/javascript"></script>
<c:choose>
    <c:when test="${cookie.eIcsLang.value == null}">
        <c:set var="lenguaje" value="es"/>
    </c:when>
    <c:otherwise>
        <c:set var="lenguaje" value="${cookie.eIcsLang.value}"/>
    </c:otherwise>
</c:choose>
<spring:url value="/resources/js/libs/sweetalert2/sweetalert2.min.js" var="sweetalert2" />
<script src="${sweetalert2}"></script>
<spring:url value="/resources/js/libs/data-tables/i18n/label_{language}.json" var="dataTablesLang">
    <spring:param name="language" value="${lenguaje}" />
</spring:url>
<script>
    jQuery(document).ready(function() {
        handleDatePickers("${lenguaje}");
        $('#etiquetas').select2();
        var table = $('#lista_casos').DataTable({
            dom:    "<'row'<'col-sm-4'B><'col-sm-4'f><'col-sm-4'l>>" +
                    "<'row'<'col-sm-12'tr>>" +
                    "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            buttons: [
                {
                    extend:'excel',
                    exportOptions: {
                        columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                    }
                },
                {
                    extend:'pdf',
                    exportOptions: {
                        columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                    }
                }
            ],
            "oLanguage": {
                "sUrl": "${dataTablesLang}"
            },
            responsive: true,
            searching: true,
            paging: true
        });

        $("#lista_casos tbody").on("click", ".desact",function(){
            $('#accionUrl').val($(this).data('id').substr(0,$(this).data('id').lastIndexOf("-")));
            $('#titulo').html('<h2 class="modal-title">'+"${confirmar}"+'</h2>');
            $('#cuerpo').html('<h3>'+"${deshabilitar}"+' '+decodeURIComponent($(this).data('id').substr($(this).data('id').lastIndexOf("-")+1))+'?</h3>');
            $('#btnOkAct').show();
            $('#btnOkPrint').hide();
            $('#dvSalida').hide();
            $('#btnOkClose').hide();
            $('#basic').modal('show');
        });

        if ("${deshabilitado}"){
            Swal.fire({
                icon: 'success',
                title: "${casa}",
                text: "${recordDisabledLabel}",
                timer: 4000
            })
        }
        if ("${cerrado}"){
            Swal.fire({
                icon: 'success',
                title: "${casa}",
                text: "${successLabel}",
                timer: 4000
            })
        }

        $("#lista_casos tbody").on("click", ".salida",function(){
            $('#accionUrl').val($(this).data('id'));
            $('#titulo').html('<h2 class="modal-title">'+"${cerrarCaso}"+'</h2>');
            $('#cuerpo').html('');
            $('#btnOkAct').hide();
            $('#btnOkPrint').hide();
            $('#dvSalida').show();
            $('#btnOkClose').show();
            $('#basic').modal('show');
        });

        var form1 = $('#print-form');
        form1.validate({
            errorElement: 'span', //default input error message container
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                visita: {
                    required: true
                },
                etiquetas: {
                    required: true
                }
            },
            ignore: ':hidden:not("#etiquetas")',
            errorPlacement: function ( error, element ) {
                // Add the `help-block` class to the error element
                error.addClass( 'form-control-feedback col-md-4' );
                if ( element.prop( 'type' ) === 'checkbox' ) {
                    error.insertAfter( element.parent( 'label' ) );
                } else {
                    error.insertAfter( element ); //cuando no es input-group
                    //error.insertAfter(element.parent('.input-group'));
                }
            },
            highlight: function ( element, errorClass, validClass ) {
                $( element ).addClass( 'form-control-danger' ).removeClass( 'form-control-success' );
                $( element ).parents( '.input-group' ).addClass( 'has-danger' ).removeClass( 'has-success' );
            },
            unhighlight: function (element, errorClass, validClass) {
                $( element ).addClass( 'form-control-success' ).removeClass( 'form-control-danger' );
                $( element ).parents( '.input-group' ).addClass( 'has-success' ).removeClass( 'has-danger' );
            },
            submitHandler: function (form) {
                $('#titulo').html('<h2 class="modal-title">'+"${confirmar}"+'</h2>');
                $('#cuerpo').html('<h3>'+'Imprimir seleccionados?</h3>');
                $('#btnOkAct').hide();
                $('#btnOkPrint').show();
                $('#dvSalida').hide();
                $('#btnOkClose').hide();
                $('#basic').modal('show');
            }
        });

        var form2 = $('#close-form');
        form2.validate({
            errorElement: 'span', //default input error message container
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                fechaSalida: {
                    required: true
                }
            },
            errorPlacement: function ( error, element ) {
                // Add the `help-block` class to the error element
                error.addClass( 'form-control-feedback col-md-8' );
                if ( element.prop( 'type' ) === 'checkbox' ) {
                    error.insertAfter( element.parent( 'label' ) );
                } else {
                    //error.insertAfter( element ); //cuando no es input-group
                    error.insertAfter(element.parent('.input-group'));
                }
            },
            highlight: function ( element, errorClass, validClass ) {
                $( element ).addClass( 'form-control-danger' ).removeClass( 'form-control-success' );
                $( element ).parents( '.form-group' ).addClass( 'has-danger' ).removeClass( 'has-success' );
            },
            unhighlight: function (element, errorClass, validClass) {
                $( element ).addClass( 'form-control-success' ).removeClass( 'form-control-danger' );
                $( element ).parents( '.form-group' ).addClass( 'has-success' ).removeClass( 'has-danger' );
            }
        });

        $("#btnOkClose").click(function(){
            if (form2.valid()){
                processCase();
            }
        });

        function processCase()
        {
            $.post( "${closeUrl}"
                    , {codigo: $('#accionUrl').val(), fechaInactiva: $('#fechaSalida').val()}
                    , function( data )
                    {
                        var registro = JSON.parse(data);
                        console.log(registro);
                        if (registro.codigoCaso === undefined) {
                            Swal.fire({
                                icon: 'error',
                                title: "${casa}",
                                text: data,
                                timer: 4000
                            })
                        }
                        else {
                            Swal.fire({
                                icon: 'success',
                                title: "${casa}",
                                text: "${successLabel}",
                                timer: 4000
                            });
                            window.setTimeout(function () {
                                window.location.reload();
                            }, 1500);
                        }
                    }
                    , 'text' )
                    .fail(function(XMLHttpRequest, textStatus, errorThrown) {
                        Swal.fire({
                            icon: 'error',
                            title: "Error",
                            text: errorThrown,
                            timer: 4000
                        })
                    });
        }

    });

    function ejecutarAccion() {
        window.location.href = $('#accionUrl').val();
    }

</script>
</body>
</html>
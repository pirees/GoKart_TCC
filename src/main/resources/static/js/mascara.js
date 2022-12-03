$(document).ready(function(){
    $('#valorBateria').mask('#.##0,00', {reverse: true});
    $('#data').mask('00/00/0000');
    $('#cep').mask('00000-000');
    $('#cnpj').mask('00.000.000/0000-00', {reverse: true});
    $('#celular').mask('(00)00000-0000');
});
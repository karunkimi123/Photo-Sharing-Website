<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
  <title>MiFotos - <c:out value="${user.userName}"/> Gallery</title>
  <link rel="stylesheet" href="/styles/style2.css" type="text/css" />
  <script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
  <script type="text/javascript" src="/js/jquery.galleriffic.js"></script>
  <script type="text/javascript" src="/js/jquery.opacityrollover.js"></script>
  <!-- Display thumbnails only when JavaScript is disabled -->
  <script type="text/javascript"><!--
    document.write('<style type="text/css">.noscript { display: none; }</style>');
  --></script>  
  
  <script type="text/javascript">

	function deleteFile( fileName )
	{
		if( confirm("Are you sure to delete " + fileName + " ?") )
		{
			location.href = "/delete?fileName=" + fileName;	
		}	
	}
	
  </script>

  <style type="text/css">
        .myfileupload-buttonbar input
        {
            position: absolute;
            top: 0;
            right: 0;
            margin: 0;
            border: solid transparent;
            border-width: 0 0 100px 200px;
            opacity: 0.0;
            filter: alpha(opacity=0);
            -o-transform: translate(250px, -50px) scale(1);
            -moz-transform: translate(-300px, 0) scale(4);
            direction: ltr;
            cursor: pointer;
        }
        .myui-button
        {
            position: relative;
            cursor: pointer;
            text-align: center;
            overflow: visible;
            background-color: red;
            overflow: hidden;
        }
    </style>
</head>

<body>

	<!-- <div id="upload" class="uploader">
   	    <form name="UploadForm" action="/upload" method="POST" enctype="multipart/form-data">
	       	<input id="selectedFile" type="file" name="fileName" style="display: none;" onchange="document.UploadForm.submit();"/>
	       	<input type="button" name="upload" value="Upload Photo" onclick="document.getElementById('selectedFile').click();"/>
        </form>
      </div>   -->
      
  <div id="header">
    <div class="page">
      <div id="header-text">
      	<h1>Photo Gallery of <c:out value="${user.userName}"/></h1>
        <div class="logout">
	        <div class="logout-controls">
	    		<a href="/logout" class="play" title="Sign Out">Sign Out</a>
	    	</div>
    	</div>
    	
    	<div class="upload">
	        <div class="upload-controls">
	        	<form name="UploadForm" action="/upload" method="POST" enctype="multipart/form-data">
	       			<input id="selectedFile" type="file" name="fileName" style="display: none;" onchange="document.UploadForm.submit();"/>
	       			<a href="#" class="upload" title="Upload Photo" onclick="document.getElementById('selectedFile').click();">Upload Photo</a>
        		</form>
	    	</div>
    	</div>
    	    	
      </div>
      
    </div>
  </div>

  <div class="page">
    <div id="container">
       		
      <div id="gallery" class="content">
        <div id="controls" class="controls"></div>
        <div class="slideshow-container">       		
          <div id="loading" class="loader"></div>
          <div id="slideshow" class="slideshow"></div>
        </div>
        <div id="caption" class="caption-container"></div>
      </div>
       	
      <div id="thumbs" class="navigation">
        <ul class="thumbs noscript">
          <!-- Photo 01 -->               
          <c:set var="count" value="${1}"/>
          <c:forEach items="${images}" var="image">
          <li onmouseover="document.getElementById('image-<c:out value='${count}'/>').style.visibility='visible';" onmouseout="document.getElementById('image-<c:out value='${count}'/>').style.visibility='hidden';">
            <a class="thumb" href="<c:out value='${image.value}'/>">
              <img src="<c:out value='${image.value}'/>" alt="Photo not Found"/>                          
              <img id="image-<c:out value='${count}'/>" style="margin-left: 55px;margin-top: -20px;width: 20px;height: 20px; visibility:hidden;" src="/images/Delete_Icon.png" alt="Delete File" onmouseover="this.visibity='visible';" onclick="javascript:deleteFile('<c:out value='${image.key}'/>');"/>
            </a>
          </li>
          <c:set var="count" value="${count+1}"/>
          </c:forEach>
          
        </ul>
      </div>

      <div style="clear: both;"></div>
    </div>
  </div>


  <!-- "Advanced Galleriffic Gallery" JavaScript code -->
  <!-- See http://www.twospy.com/galleriffic/ for more info. -->
  <script type="text/javascript"><!--
    jQuery(document).ready(function($) {
      // Only apply these styles when JavaScript is enabled
      $('div.navigation').css({'width' : '185px', 'float' : 'left'});
      $('div.content').css('display', 'block');

      // Initially set opacity on thumbs and add additional styling for hover
      // effect on thumbs
      var onMouseOutOpacity = 0.67;
      $('#thumbs ul.thumbs li').opacityrollover({
        mouseOutOpacity:   onMouseOutOpacity,
        mouseOverOpacity:  1.0,
        fadeSpeed:         'fast',
        exemptionSelector: '.selected'
      });

      // Initialize Advanced Galleriffic Gallery
      var gallery = $('#thumbs').galleriffic({
        delay:                     2500,
        numThumbs:                 14,
        preloadAhead:              10,
        enableTopPager:            true,
        enableBottomPager:         true,
        maxPagesToShow:            7,
        imageContainerSel:         '#slideshow',
        controlsContainerSel:      '#controls',
        captionContainerSel:       '#caption',
        loadingContainerSel:       '#loading',
        renderSSControls:          true,
        renderNavControls:         true,
        playLinkText:              'Play Slideshow',
        pauseLinkText:             'Pause Slideshow',
        prevLinkText:              '&lsaquo; Previous Photo',
        nextLinkText:              'Next Photo &rsaquo;',
        nextPageLinkText:          '&rsaquo;',
        prevPageLinkText:          '&lsaquo;',
        enableHistory:             false,
        autoStart:                 false,
        syncTransitions:           true,
        defaultTransitionDuration: 900,
        onSlideChange:             function(prevIndex, nextIndex) {
          // 'this' refers to the gallery, which is an extension of $('#thumbs')
          this.find('ul.thumbs').children()
            .eq(prevIndex).fadeTo('fast', onMouseOutOpacity).end()
            .eq(nextIndex).fadeTo('fast', 1.0);
        },
        onPageTransitionOut:       function(callback) {
          this.fadeTo('fast', 0.0, callback);
        },
        onPageTransitionIn:        function() {
          this.fadeTo('fast', 1.0);
        }
      });
    });
  --></script>

</body>

</html>
"use strict";

function blogGrid() {
  //adding CSS classes for elements that needs different styles depending on they widht width
  //see 'plugins.js' file
  var $gridElements = jQuery('.gallery-grid .isotope-item');
  $gridElements.addWidthClass({
    breakpoints: [300, 500, 600]
  });
  $gridElements.each(function(){
    var $img = jQuery(this).find('.item-media img');
    $img.css({ visibility: 'hidden'}).parent().css({backgroundImage: 'url(' + $img.attr('src') +')'});
  });

}

function pieChart() {
  //circle progress bar
  if (jQuery().easyPieChart) {
    var count = 0 ;
    // var colors = ['#55bce9', '#7370b5', '#6496cf'];
    var colors = ['#c14240'];
    
    jQuery('.chart').each(function(){
      var imagePos = jQuery(this).offset().top;
      var topOfWindow = jQuery(window).scrollTop();
      if (imagePos < topOfWindow+900) {
        jQuery(this).easyPieChart({
          barColor: colors[count],
          trackColor: '#ffffff',
          scaleColor: false,
          scaleLength: false,
          lineCap: 'butt',
          lineWidth: 20,
          size: 270,
          rotate: 0,
          animate: 3000,
          onStep: function(from, to, percent) {
            jQuery(this.el).find('.percent').text(Math.round(percent));
          }
        });
      }
      count++;
      if (count >= colors.length) { count = 0};
    });
  }
}

function affixSidebarInit() {
  var $affixAside = jQuery('.affix-aside');
  if ($affixAside.length) {
  
  //on stick and unstick event
  $affixAside.on('affix.bs.affix', function(e) {
    var affixWidth = $affixAside.width();
    var affixLeft = $affixAside.offset().left;
    $affixAside
      .width(affixWidth)
      .css("left", affixLeft);
  }).on('affix-top.bs.affix affix-bottom.bs.affix', function(e) {
    $affixAside.css({"width": "", "left": ""});
  });

  //counting offset
  var offsetTop = $affixAside.offset().top - jQuery('.page_header').height();
  var offsetBottom = jQuery('.page_footer').outerHeight(true) + jQuery('.page_copyright').outerHeight(true);
  $affixAside.affix({
    offset: {
      top: offsetTop,
      bottom: offsetBottom
    },
  });

  $affixAside.affix('checkPosition');
  jQuery(window).trigger('resize');
  jQuery(window).on('resize', function() {
    $affixAside.css({"width": "", "left": ""});
    var offsetTop = $affixAside.offset().top - jQuery('.page_header').height();
    var offsetBottom = jQuery('.page_footer').outerHeight(true) + jQuery('.page_copyright').outerHeight(true);
    $affixAside.data('bs.affix').options.offset.top = offsetTop;
    $affixAside.data('bs.affix').options.offset.bottom = offsetBottom;
    $affixAside.affix('checkPosition');
    });
  }//eof checking of affix sidebar existing
}

//function that initiating template plugins on document.ready event
function documentReadyInit() {
  ///////////
  //Plugins//
  ///////////
  //contact form processing
  jQuery('form.contact-form').on('submit', function( e ){
    e.preventDefault();
    var $form = jQuery(this);
    jQuery($form).find('span.contact-form-respond').remove();
      //checking on empty values
      jQuery($form).find('[aria-required="true"], [required]').each(function(index) {
        if (!jQuery(this).val().length) {
          jQuery(this).addClass('invalid').on('focus', function(){jQuery(this).removeClass('invalid')});
        }
      });
      //if one of form fields is empty - exit
      if ($form.find('[aria-required="true"], [required]').hasClass('invalid')) {
        return;
      }
      
      //sending form data to PHP server if fields are not empty
      var request = $form.serialize();
      var ajax = jQuery.post( "contact-form.php", request )
      .done(function( data ) {
          jQuery($form).find('[type="submit"]').attr('disabled', false).parent().append('<span class="contact-form-respond highlight">'+data+'</span>');
      })
      .fail(function( data ) {
          jQuery($form).find('[type="submit"]').attr('disabled', false).parent().append('<span class="contact-form-respond highlight">Mail cannot be sent. You need PHP server to send mail.</span>');
      })
  });
  
  //search modal
  jQuery(".search_modal_button").on('click', function(e){
    e.preventDefault();
    jQuery('#search_modal').modal('show').find('input').first().focus();
  });

  //search form processing
  jQuery('form.searchform').on('submit', function( e ){
    e.preventDefault();
    var $form = jQuery(this);
    var $searchModal = jQuery('#search_modal');
    $searchModal.find('div.searchform-respond').remove();
    
    //checking on empty values
    jQuery($form).find('[type="text"]').each(function(index) {
      if (!jQuery(this).val().length) {
        jQuery(this).addClass('invalid').on('focus', function(){jQuery(this).removeClass('invalid')});
      }
    });
    
    //if one of form fields is empty - exit
    if ($form.find('[type="text"]').hasClass('invalid')) {
      return;
    }
    $searchModal.modal('show');
    
    //sending form data to PHP server if fields are not empty
    var request = $form.serialize();
    var ajax = jQuery.post( "search.php", request )
    .done(function( data ) {
      $searchModal.append('<div class="searchform-respond">'+data+'</div>');
    })
    .fail(function( data ) {
      $searchModal.append('<div class="searchform-respond">Search cannot be done. You need PHP server to search.</div>');
    })
  });

  //mailchimp subscribe form processing
  jQuery('.signup').on('submit', function( e ) {
    e.preventDefault();
    var $form = jQuery(this);
    // update user interface
    $form.find('.response').html('Adding email address...');
    // Prepare query string and send AJAX request
    jQuery.ajax({
      url: 'mailchimp/store-address.php',
      data: 'ajax=true&email=' + escape($form.find('.mailchimp_email').val()),
      success: function(msg) {
        $form.find('.response').html(msg);
      }
    });
  });

  //twitter
  if (jQuery().tweet) {
    jQuery('.twitter').tweet({
      modpath: "./twitter/",
      count: 2,
      avatar_size: 48,
      loading_text: 'loading twitter feed...',
      join_text: 'auto',
      username: 'ThemeForest', 
      template: "{avatar}<div class=\"tweet_right\">{time}{join}<span class=\"tweet_text\">{tweet_text}</span></div>"
    });
  }
  
  //mainmenu
  if (jQuery().superfish) {
    jQuery('ul.sf-menu').superfish({
      delay:       300,
      animation:   {opacity:'show'},
      animationOut: {opacity: 'hide'},
      speed:       'fast',
      disableHI:   false,
      cssArrows:   true,
      autoArrows:  true
    });
  }

  //toggle mobile menu
  jQuery('.toggle_menu').on('click', function(){
    jQuery('.toggle_menu').toggleClass('mobile-active');
    jQuery('.page_header').toggleClass('mobile-active');
  });

  jQuery('.mainmenu a').on('click', function(){
    if (!jQuery(this).hasClass('sf-with-ul')) {
      jQuery('.toggle_menu').toggleClass('mobile-active');
      jQuery('.page_header').toggleClass('mobile-active');
    }
  });

  //single page localscroll and scrollspy
  var navHeight = jQuery('.page_header').outerHeight(true);
  jQuery('body').scrollspy({
    target: '.mainmenu_wrapper',
    offset: navHeight
  });
  if (jQuery().localScroll) {
    jQuery('.mainmenu, #land').localScroll({
      duration:900,
      easing:'easeInOutQuart',
      offset: -navHeight+10
    });
  }

  //toTop
  if (jQuery().UItoTop) {
    jQuery().UItoTop({ easingType: 'easeOutQuart' });
  }

  //parallax
  if (jQuery().parallax) {
    jQuery('.parallax').parallax("50%", 0.01);
  }

  //prettyPhoto
  if (jQuery().prettyPhoto) {
    jQuery("a[data-gal^='prettyPhoto']").prettyPhoto({
      hook: 'data-gal',
      theme: 'facebook' /* light_rounded / dark_rounded / light_square / dark_square / facebook / pp_default*/
    });
  }
  
  //carousel
  if (jQuery().carousel) {
      jQuery('.carousel').carousel();
  }
  
  //owl carousel
  if (jQuery().owlCarousel) {
    jQuery('.owl-carousel').each(function() {
      var $carousel = jQuery(this);
      var loop = $carousel.data('loop') ? $carousel.data('loop') : false;
      var margin = ($carousel.data('margin') || $carousel.data('margin') == 0) ? $carousel.data('margin') : 30;
      var nav = $carousel.data('nav') ? $carousel.data('nav') : false;
      var dots = $carousel.data('dots') ? $carousel.data('dots') : false;
      var themeClass = $carousel.data('themeclass') ? $carousel.data('themeclass') : 'owl-theme';
      var center = $carousel.data('center') ? $carousel.data('center') : false;
      var items = $carousel.data('items') ? $carousel.data('items') : 4;
      var autoplay = $carousel.data('autoplay') ? $carousel.data('autoplay') : false;
      var responsiveXs = $carousel.data('responsive-xs') ? $carousel.data('responsive-xs') : 1;
      var responsiveSm = $carousel.data('responsive-sm') ? $carousel.data('responsive-sm') : 2;
      var responsiveMd = $carousel.data('responsive-md') ? $carousel.data('responsive-md') : 3;
      var responsiveLg = $carousel.data('responsive-lg') ? $carousel.data('responsive-lg') : 4;
      // var responsive = $carousel.data('responsive') ? jQuery.parseJSON($carousel.data('responsive')) : {0:{items:1},767:{items:2},992:{items:2},1200:{items: 4}};

      $carousel.owlCarousel({
        loop: loop,
        margin: margin,
        nav: nav,
        autoplay: autoplay,
        dots: dots,
        themeClass: themeClass,
        center: center,
        items: items,
        responsive: {
          0:{
            items: responsiveXs
          },
          767:{
            items: responsiveSm
          },
          992:{
            items: responsiveMd
          },
          1200:{
            items: responsiveLg
          }
        },
      })
    });
  } //eof owl-carousel
  
  //comingsoon counter
  if (jQuery().countdown) {
    //today date plus month for demo purpose
    var demoDate = new Date();
    demoDate.setMonth(demoDate.getMonth()+1);
    jQuery('#comingsoon-countdown').countdown({until: demoDate});
  }
  
  
  /////////
  //shop///
  /////////
  jQuery('#toggle_shop_view').on('click', function( e ) {
    e.preventDefault();
    jQuery(this).toggleClass('grid-view');
    jQuery('#products').toggleClass('grid-view list-view');
  });

  //zoom image
  if (jQuery().elevateZoom) {
    jQuery('#product-image').elevateZoom({
      gallery: 'product-image-gallery',
      cursor: 'pointer', 
      galleryActiveClass: 'active', 
      responsive:true, 
      loadingIcon: 'img/AjaxLoader.gif'
    });
  }

  //add review button
  jQuery('.review-link').on('click', function( e ) {
    var thisLink = jQuery(this);
    var reviewTabLink = jQuery('a[href="#reviews_tab"]');
    //show tab only if it's hidden
    if (!reviewTabLink.parent().hasClass('active')) {
      reviewTabLink
      .tab('show')
      .on('shown.bs.tab', function (e) {
          jQuery(window).scrollTo(jQuery(thisLink).attr('href'), 400);
      })
    }
    jQuery(window).scrollTo(jQuery(thisLink).attr('href'), 400);
  });

  //product counter
  jQuery('.plus, .minus').on('click', function( e ) {
    var numberField = jQuery(this).parent().find('[type="number"]');
    var currentVal = numberField.val();
    var sign = jQuery(this).val();
    if (sign === '-') {
      if (currentVal > 1) {
        numberField.val(parseFloat(currentVal) - 1);
      }
    } else {
      numberField.val(parseFloat(currentVal) + 1);
    }
  });

  //remove product from cart
  jQuery('a.remove').on('click', function( e ) {
    e.preventDefault();
    jQuery(this).closest('tr, .media').remove();
  });

  //price filter
  if (jQuery().slider) {
    jQuery( ".slider-range-price" ).slider({
      range: true,
      min: 0,
      max: 100000,
      values: [ 1500, 30000 ],
      slide: function( event, ui ) {
        jQuery( ".slider_price_min" ).val( ui.values[ 0 ] );
        jQuery( ".slider_price_max" ).val( ui.values[ 1 ] );
      }
    });
    jQuery( ".slider_price_min" ).val( jQuery( ".slider-range-price" ).slider( "values", 0 ) );
    jQuery( ".slider_price_max" ).val( jQuery( ".slider-range-price" ).slider( "values", 1 ) );
  }

  //color filter 
  jQuery(".color-filters").find("a[data-background-color]").each(function() {
    jQuery(this).css({"background-color" : jQuery(this).data("background-color")});
  });

  
  //background image teaser
  jQuery(".bg_teaser").each(function(){
    var $teaser = jQuery(this);
    var imagePath = $teaser.find("img").first().attr("src");
    $teaser.css("background-image", "url(" + imagePath + ")");
    if (!$teaser.find('.bg_overlay').length) {
      $teaser.prepend('<div class="bg_overlay"/>');
    }
  });

  //bootstrap tab - show first tab 
  jQuery('.nav-tabs').each(function() {
    jQuery(this).find('a').first().tab('show');
  });
  jQuery('.tab-content').each(function() {
    jQuery(this).find('.tab-pane').first().addClass('fade in');
  });

  //bootstrap collapse - show first tab 
  jQuery('.panel-group').each(function() {
    jQuery(this).find('a').first().filter('.collapsed').trigger('click');
  });
}//eof documentReadyInit

//function that initiating template plugins on window.load event
function windowLoadInit() {
  //gallery grid
  blogGrid();




  //tooltip
  if (jQuery().tooltip) {
    jQuery('[data-toggle="tooltip"]').tooltip();
  }

  //chart
  pieChart();
  
  //flexslider
  if (jQuery().flexslider) {
    var $introSlider = jQuery(".intro_section .flexslider");
    $introSlider.each(function(index){
    // if ($introSlider.length) {
      var $currentSlider = jQuery(this);
      $currentSlider.flexslider({
        animation: "fade",
        pauseOnHover: true, 
        useCSS: true,
        controlNav: false,   
        directionNav: true,
        prevText: "Prev",
        nextText: "Next",
        smoothHeight: false,
        slideshowSpeed:10000,
        animationSpeed:600,
        start: function( slider ) {
          slider.find('.slide_description').children().css({'visibility': 'hidden'});
            slider.find('.flex-active-slide .slide_description').children().each(function(index){
            var self = jQuery(this);
            var animationClass = !self.data('animation') ? 'fadeInRight' : self.data('animation');
            setTimeout(function(){
              self.addClass("animated "+animationClass);
            }, index*200);
          });
        },
        after :function( slider ){
          slider.find('.flex-active-slide .slide_description').children().each(function(index){
            var self = jQuery(this);
            var animationClass = !self.data('animation') ? 'fadeInRight' : self.data('animation');
            setTimeout(function(){
              self.addClass("animated "+animationClass);
            }, index*200);
          });
        },
        end :function( slider ){
          slider.find('.slide_description').children().each(function() {
            var self = jQuery(this);
            var animationClass = !self.data('animation') ? 'fadeInRight' : self.data('animation');
            self.removeClass('animated ' + animationClass).css({'visibility': 'hidden'});
              // jQuery(this).attr('class', '');
          });
        },
    // });
    })
      
    //wrapping nav with container
    .find('.flex-control-nav')
    .wrap('<div class="container nav-container"/>')
    });
    //}//eof introSlider check

    jQuery(".flexslider").each(function(index){
      var $currentSlider = jQuery(this);
      //exit if intro slider already activated 
      if ($currentSlider.find('.flex-active-slide').length) {
        return;
      }
      $currentSlider.flexslider({
        animation: "fade",
        useCSS: true,
        controlNav: true,   
        directionNav: false,
        prevText: "",
        nextText: "",
        smoothHeight: false,
        slideshowSpeed:5000,
        animationSpeed:800,
      })
    });
  }

  ////////////////////
  //header processing/
  ////////////////////
  //stick header to top
  //wrap header with div for smooth sticking
  var $header = jQuery('.page_header').first();
  if ($header.length) {
    var headerHeight = $header.outerHeight();
    $header.wrap('<div class="page_header_wrapper"></div>').parent().css({height: headerHeight}); //wrap header for smooth stick and unstick
    //get offset
    var headerOffset = 0;
    //check for sticked template headers
    // if (!jQuery(document).scrollTop()) {
    // if (jQuery(document).scrollTop() < $header.offset().top) {
    headerOffset = $header.offset().top;
    // }
    // }
    jQuery($header).affix({
      offset: {
        top: headerOffset,
        bottom: 0
      }
    });
    //if header has different height on afixed and affixed-top positions - correcting wrapper height
    jQuery($header).on('affixed-top.bs.affix', function () {
      $header.parent().css({height: $header.outerHeight()});
    });
  }
  
  ///////////////
  //aside affix//
  ///////////////
  affixSidebarInit();
  
  //preloader
  jQuery(".preloaderimg").fadeOut();
  jQuery(".preloader").delay(200).fadeOut("slow").delay(200, function(){
    jQuery(this).remove();
  });
  
  jQuery('body').scrollspy('refresh');
  
  //animation to elements on scroll
  if (jQuery().appear) {
    jQuery('.to_animate').appear();
    jQuery('.to_animate').filter(':appeared').each(function(index){
        var self = jQuery(this);
        var animationClass = !self.data('animation') ? 'fadeInUp' : self.data('animation');
        var animationDelay = !self.data('delay') ? 210 : self.data('delay');
        setTimeout(function(){
            self.addClass("animated " + animationClass);
        }, index * animationDelay);
    });
    
    jQuery('body').on('appear', '.to_animate', function(e, $affected ) {
      jQuery($affected).each(function(index){
        var self = jQuery(this);
        var animationClass = !self.data('animation') ? 'fadeInUp' : self.data('animation');
        var animationDelay = !self.data('delay') ? 210 : self.data('delay');
        setTimeout(function(){
          self.addClass("animated " + animationClass);
        }, index * animationDelay);
      });
    });
  }

  //counters init on scroll
  if (jQuery().appear) {
    jQuery('.counter').appear();
    jQuery('.counter').filter(':appeared').each(function(index){
      if (jQuery(this).hasClass('counted')) {
        return;
      } else {
        jQuery(this).countTo().addClass('counted');
      }
    });
    jQuery('body').on('appear', '.counter', function(e, $affected ) {
      jQuery($affected).each(function(index){
        if (jQuery(this).hasClass('counted')) {
          return;
        } else {
          jQuery(this).countTo().addClass('counted');
        }
      });
    });
  }
  
  //bootstrap animated progressbar
  if (jQuery().appear) {
    if (jQuery().progressbar) {
      jQuery('.progress .progress-bar').appear();
      jQuery('.progress .progress-bar').filter(':appeared').each(function(index){
        jQuery(this).progressbar({
          transition_delay: 300
        });
      });
      jQuery('body').on('appear', '.progress .progress-bar', function(e, $affected ) {
        jQuery($affected).each(function(index){
          jQuery(this).progressbar({
            transition_delay: 300
          });
        });
      });
      //animate progress bar inside bootstrap tab
      jQuery('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
        jQuery(jQuery(e.target).attr('href')).find('.progress .progress-bar').progressbar({
          transition_delay: 300
        });
      });
    }
  }
  
  //flickr
  // use http://idgettr.com/ to find your ID
  if (jQuery().jflickrfeed) {
    jQuery("#flickr").jflickrfeed({
      flickrbase: "http://api.flickr.com/services/feeds/",
      limit: 8,
      qstrings: {
        id: "131791558@N04"
      },
      itemTemplate: '<a href="{{image_b}}" data-gal="prettyPhoto[pp_gal]"><li><img alt="{{title}}" src="{{image_s}}" /></li></a>'
    }, function(data) {
      jQuery("#flickr a").prettyPhoto({
        hook: 'data-gal',
        theme: 'facebook'
      });
    });
  }
  
  //video images preview
  jQuery('.embed-placeholder').each(function(){
      jQuery(this).on('click', function(e) {
          e.preventDefault();
          jQuery(this).replaceWith('<iframe class="embed-responsive-item" src="'+ jQuery(this).attr('href') + '?rel=0&autoplay=1'+ '"></iframe>');
      });
  });
}//eof windowLoadInit

jQuery(document).ready(function() {
  documentReadyInit();
  
  // The slider being synced must be initialized first
  $('#carousel').flexslider({
    animation: "slide",
    controlNav: false,
    animationLoop: false,
    slideshow: false,
    itemWidth: 210,
    itemMargin: 5,
    asNavFor: '#slider'
  });
 
  $('#slider').flexslider({
    animation: "slide",
    controlNav: false,
    animationLoop: false,
    slideshow: false,
    sync: "#carousel"
  });
  
  jQuery('.page-breadcrumbs__title').each(function () {
    this.innerHTML = this.innerHTML.replace( /^(.+?\s)/, '<span>$1</span>' );
  });

  $("#gallery_item-carousel").owlCarousel({
    items : 4,
    nav: true,
    dots: false,
    margin:30,
    responsive:{
      0:{
        items:1
      },
      600:{
        items:2
      },
      1000:{
        items:4
      }
    }
  });
  
  $("#gallery_blog-carousel").owlCarousel({
    items : 4,
    nav: true,
    dots: false,
    margin:30,
    responsive:{
      0:{
        items:1
      },
      600:{
        items:2
      },
      1000:{
        items:3
      }
    }
  });
  
}); //end of "document ready" event

jQuery(window).load(function(){
  windowLoadInit();
  
  
  $('.gallery-banners').isotope({
    itemSelector: '.gallery-banners-item',
    masonry: {
      layoutMode: 'packery',
      columnWidth: 360,
      gutter: 18
    }
  });
  
  
}); //end of "window load" event

jQuery(window).resize(function(){
  blogGrid();
  jQuery('body').scrollspy('refresh');
  
  //header processing
  var $header = jQuery('.page_header').first();
    //checking document scrolling position
  if ($header.length && !jQuery(document).scrollTop() && $header.first().data('bs.affix')) {
    $header.first().data('bs.affix').options.offset.top = $header.offset().top;
  }
  jQuery(".page_header_wrapper").css({height: $header.first().outerHeight()}); //editing header wrapper height for smooth stick and unstick
});

jQuery(window).scroll(function() {
  //circle progress bar
  pieChart();
});
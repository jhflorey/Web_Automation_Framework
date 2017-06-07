Router.configure({
  layoutTemplate: 'layout'
});

Router.map(function(){
  this.route('photos', {
    path:'/',
    template:'photos'
  });
});

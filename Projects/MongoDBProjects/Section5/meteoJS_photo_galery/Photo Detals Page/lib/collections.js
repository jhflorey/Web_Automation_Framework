Photos = new FS.Collection('Photos', {
  stores: [new FS.Store.GridFS('Photos')],
    filter: {
      allow: {
        contentTypes: ['image/*']
      },
      onInvalid: function(message){
        FlashMessage.sendError(message);
      }
    }
});

Photos.allow({
  insert: function(){return true;},
  download: function(){return true;}
});

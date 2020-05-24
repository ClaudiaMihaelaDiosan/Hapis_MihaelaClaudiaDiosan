// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();

exports.notification = functions.database.ref('throughVolunteerDonations/{uid}/{delivered}')
.onWrite(async(change, context)=>{
  var title = 'La entrega se ha realizado a' + change.after.val().homelessUsername + '.'
  var body = change.after.val().message
  console.log(title)
  console.log(body)
  var payload = {
    notification: {
      title: title,
      body: body
    }
  };
  var ids = context.params.uid.split("_");
  conole.log(ids);
  var userRef;
  if (change.after.val().donorEmail === ids[0]){
    userRef = db.collection('donors').doc(ids[1]);
  }else {
    userRef = db.collection('donors').doc(ids[0]);
  }
  return userRef
  .get()
  .then(doc =>{
    if (!doc.exists){
      throw new ('No such User document!');
    }else{
      console.log("enviado")
      admin.messaging().sendToDevice(doc.data().tok,payload)
      return true
    }
  })
  .catch(err =>{
    console.log(err)
    return false;
  });
});

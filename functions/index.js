const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
const db = admin.firestore();

exports.sendDeliveredNotification = functions.firestore
    .document('throughVolunteerDonations/{mUid}')
    .onUpdate((change, context) =>{
     const data = change.after.data();
     const title = data.title;
     const content = data.content;

     if (data.delivered === true){
       var payload = {
         notification: {
           title: title,
           body: content,
         }
       };

       var donorRef = db.collection('donors').doc(data.donorEmail);
         return donorRef
             .get()
             .then(doc => {
               if (!doc.exists) {
                 throw new Error('No such User document!');
               } else {
                 console.log("Sended")
                 admin.messaging().sendToDevice(doc.data().idToken, payload)
                 return true
               }
             })
             .catch(err => {
               console.log(err)
               return false;
             });
  }
  return false;
});

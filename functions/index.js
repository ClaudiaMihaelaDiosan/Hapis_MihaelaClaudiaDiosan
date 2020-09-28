const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.sendNotificationToChange =functions.firestore.document('throughVolunteerDonations/{mUid}').onWrite(async (event) => {
  let donorEmail = event.after.get('donorEmail');
  let title = event.after.get('title');
  let content = event.after.get('content');
  let donorDoc = await admin.firestore().doc('donors/'+ donorEmail).get();
  let idToken = donorDoc.get('idToken');

var payload = {
  notification: {
    title: title,
    body: content,
  }
};



let response = await admin.messaging().sendToDevice(idToken, payload);
console.log(response);

});

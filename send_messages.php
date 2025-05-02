{\rtf1\ansi\ansicpg1252\cocoartf2639
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 Menlo-Bold;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red47\green180\blue29;\red0\green0\blue0;\red192\green192\blue192;
\red159\green160\blue28;\red46\green174\blue187;\red20\green153\blue2;}
{\*\expandedcolortbl;;\cssrgb\c20241\c73898\c14950;\csgray\c0;\cssrgb\c79890\c79890\c79890;
\cssrgb\c68469\c68012\c14211;\cssrgb\c20199\c73241\c78251;\cssrgb\c0\c65000\c0;}
\paperw11900\paperh16840\margl1440\margr1440\vieww28600\viewh14980\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\b\fs22 \cf2 \CocoaLigature0 <?php
\f1\b0 \cf3 \
\cf4 header(
\f0\b \cf5 'Content-Type: application/json'
\f1\b0 \cf3 );\
\
\cf6 $username\cf3  = 
\f0\b \cf5 "s2656353"
\f1\b0 \cf3 ;\
\cf6 $password\cf3  = 
\f0\b \cf5 "s2656353"
\f1\b0 \cf3 ;\
\cf6 $database\cf3  = 
\f0\b \cf5 "d2656353"
\f1\b0 \cf3 ;\
\
\cf6 $link\cf3  = \cf4 mysqli_connect(
\f0\b \cf5 "127.0.0.1"
\f1\b0 \cf3 , \cf6 $username\cf3 , \cf6 $password\cf3 , \cf6 $database\cf3 );\
\

\f0\b \cf6 if
\f1\b0 \cf3  (!\cf6 $link\cf3 ) \{\
    
\f0\b \cf6 echo
\f1\b0 \cf3  \cf4 json_encode(\cf3 [
\f0\b \cf5 "success"
\f1\b0 \cf3  => false, 
\f0\b \cf5 "message"
\f1\b0 \cf3  => 
\f0\b \cf5 "Database connection failed"
\f1\b0 \cf3 ]);\
    exit;\
\}\
\
\cf6 $requester_email\cf3  = \cf6 $_POST\cf3 [
\f0\b \cf5 "email"
\f1\b0 \cf3 ];\
\cf6 $volunteer_id\cf3  = \cf6 $_POST\cf3 [
\f0\b \cf5 "volunteer_id"
\f1\b0 \cf3 ];\
\cf6 $message\cf3  = \cf6 $_POST\cf3 [
\f0\b \cf5 "message"
\f1\b0 \cf3 ];\
\
\cf6 $query\cf3  = 
\f0\b \cf5 "SELECT id FROM users WHERE email = '$requester_email' LIMIT 1"
\f1\b0 \cf3 ;\
\cf6 $result\cf3  = \cf4 mysqli_query(\cf6 $link\cf3 , \cf6 $query\cf3 );\
\

\f0\b \cf6 if
\f1\b0 \cf3  (!\cf6 $result\cf3  || \cf4 mysqli_num_rows(\cf6 $result\cf3 ) === 0) \{\
    
\f0\b \cf6 echo
\f1\b0 \cf3  \cf4 json_encode(\cf3 [
\f0\b \cf5 "success"
\f1\b0 \cf3  => false, 
\f0\b \cf5 "message"
\f1\b0 \cf3  => 
\f0\b \cf5 "Requester not found"
\f1\b0 \cf3 ]);\
    \cf4 mysqli_close(\cf6 $link\cf3 );\
    exit;\
\}\
\
\cf6 $row\cf3  = \cf4 mysqli_fetch_assoc(\cf6 $result\cf3 );\
\cf6 $requester_id\cf3  = \cf6 $row\cf3 [
\f0\b \cf5 'id'
\f1\b0 \cf3 ];\
\
\cf6 $insert\cf3  = "INSERT INTO messages (requester_id, volunteer_id, message)\cb7  \cb1 \
           VALUES (
\f0\b \cf5 '$requester_id'
\f1\b0 \cf3 , 
\f0\b \cf5 '$volunteer_id'
\f1\b0 \cf3 , 
\f0\b \cf5 '$message'
\f1\b0 \cf3 )";\
\

\f0\b \cf6 if
\f1\b0 \cf3  (\cf4 mysqli_query(\cf6 $link\cf3 , \cf6 $insert\cf3 )) \{\
    
\f0\b \cf6 echo
\f1\b0 \cf3  \cf4 json_encode(\cf3 [
\f0\b \cf5 "success"
\f1\b0 \cf3  => true, 
\f0\b \cf5 "message"
\f1\b0 \cf3  => 
\f0\b \cf5 "Message sent successfully"
\f1\b0 \cf3 ]);\
\} 
\f0\b \cf6 else
\f1\b0 \cf3  \{\
    
\f0\b \cf6 echo
\f1\b0 \cf3  \cf4 json_encode(\cf3 [
\f0\b \cf5 "success"
\f1\b0 \cf3  => false, 
\f0\b \cf5 "message"
\f1\b0 \cf3  => \cf4 mysqli_error(\cf6 $link\cf3 )]);\
\}\
\
\cf4 mysqli_close(\cf6 $link\cf3 );\

\f0\b \cf2 ?>
\f1\b0 \cf3 \
}
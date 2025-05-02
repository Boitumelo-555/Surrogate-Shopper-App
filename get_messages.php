{\rtf1\ansi\ansicpg1252\cocoartf2639
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 Menlo-Bold;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red47\green180\blue29;\red0\green0\blue0;\red46\green174\blue187;
\red159\green160\blue28;\red192\green192\blue192;}
{\*\expandedcolortbl;;\cssrgb\c20241\c73898\c14950;\csgray\c0;\cssrgb\c20199\c73241\c78251;
\cssrgb\c68469\c68012\c14211;\cssrgb\c79890\c79890\c79890;}
\paperw11900\paperh16840\margl1440\margr1440\vieww28600\viewh14960\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\b\fs22 \cf2 \CocoaLigature0 <?php
\f1\b0 \cf3 \
\
\cf4 $username\cf3  = 
\f0\b \cf5 "s2656353"
\f1\b0 \cf3 ;\
\cf4 $password\cf3  = 
\f0\b \cf5 "s2656353"
\f1\b0 \cf3 ;\
\cf4 $database\cf3  = 
\f0\b \cf5 "d2656353"
\f1\b0 \cf3 ;\
\cf4 $link\cf3  = \cf6 mysqli_connect(
\f0\b \cf5 "127.0.0.1"
\f1\b0 \cf3 , \cf4 $username\cf3 , \cf4 $password\cf3 , \cf4 $database\cf3 );\
\
\

\f0\b \cf4 if
\f1\b0 \cf3  (!\cf4 $link\cf3 ) \{\
    \cf6 die(
\f0\b \cf5 "Connection failed: "
\f1\b0 \cf3  . \cf6 mysqli_connect_error(\cf3 ));\
\}\
\
\cf4 $user_id\cf3  = 2;\
\
\cf4 $sql\cf3  = 
\f0\b \cf5 "SELECT * FROM messages WHERE volunteer_id = $user_id OR requester_id = $user_id"
\f1\b0 \cf3 ;\
\cf4 $result\cf3  = \cf6 mysqli_query(\cf4 $link\cf3 , \cf4 $sql\cf3 );\
\
\cf4 $messages\cf3  = [];\

\f0\b \cf4 while
\f1\b0 \cf3  (\cf4 $row\cf3  = \cf6 mysqli_fetch_assoc(\cf4 $result\cf3 )) \{\
    \cf4 $messages\cf3 [] = \cf4 $row\cf3 ;\
\}\
\
\cf6 header(
\f0\b \cf5 'Content-Type: application/json'
\f1\b0 \cf3 );\

\f0\b \cf4 echo
\f1\b0 \cf3  \cf6 json_encode(\cf4 $messages\cf3 );\

\f0\b \cf2 ?>
\f1\b0 \cf3 \
\
}
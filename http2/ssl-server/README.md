## Create a Key

keytool -genkey -alias http2service -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore http2service.p12 -validity 365
输入密钥库口令:  
再次输入新口令:
您的名字与姓氏是什么?
[Unknown]:  BOSIMA
您的组织单位名称是什么?
[Unknown]:  BOSIMA
您的组织名称是什么?
[Unknown]:  BOSIMA
您所在的城市或区域名称是什么?
[Unknown]:  Beijing
您所在的省/市/自治区名称是什么?
[Unknown]:  Beijing
该单位的双字母国家/地区代码是什么?
[Unknown]:  CN
CN=BOSIMA, OU=BOSIMA, O=BOSIMA, L=Beijing, ST=Beijing, C=CN是否正确?
[否]:  Y

正在为以下对象生成 2,048 位RSA密钥对和自签名证书 (SHA256withRSA) (有效期为 365 天):
CN=BOSIMA, OU=BOSIMA, O=BOSIMA, L=Beijing, ST=Beijing, C=CN


# wechatA16
## 微信6.7.3之A16数据

 **登陆类：com.tencent.mm.modelsimple.q**  构造方法
 ***
```java
public q(String arg14, String arg15, int arg16, String arg17, String arg18, String arg19, int arg20, String arg21, boolean arg22, boolean arg23) {
        this.ezE = "";
        this.ezF = "";
        this.account = "";
        this.ezG = false;
        this.ezH = false;
        this.errType = 0;
        this.errCode = 0;
        this.edu = 3;
        this.ezI = 0;
        this.ezJ = 0;
        this.ezL = false;
        y.d("MicroMsg.NetSceneManualAuth", "summerauth NetSceneManualAuth this: " + this + " account: " + arg14 + " secCodetype: " + arg16 + " secCode: " + arg17 + " sid: " + arg18 + " encryptKey: " + arg19 + " inputType: " + arg20 + " authTicket: " + arg21 + " useRawPwd: " + arg22 + " isMobileAutoLogin: " + arg23 + " stack: " + bk.csb());
        this.ezH = arg23;
        this.account = arg14;
        this.edR = new av(701);
		********方法
        d v2 = (d)this.edR.Kv();
        int v3 = av.HD().getInt("key_auth_update_version", 0);
        y.d("MicroMsg.NetSceneManualAuth", "summerauth updateVersion:%d, clientVersion:%d", new Object[]{((int)v3), ((int)com.tencent.mm.protocal.d.spa)});
        if(v3 == 0) {
            v2.spI = 1;
            com.tencent.mm.plugin.report.f.nEG.a(0x94L, 0L, 1L, false);
            v2.spx = true;
        }
        else if(v3 < com.tencent.mm.protocal.d.spa) {
            v2.spI = 16;
            com.tencent.mm.plugin.report.f.nEG.a(0x94L, 1L, 1L, false);
        }
        else {
            v2.spI = 1;
        }
		........
}
```


***com.tencent.mm.model.av***

```java
public final class av extends k implements c {
  
}
```
* 父类：com.tencent.mm.ah.k找到Kv方法
```java
public abstract class k implements q {
    @Override  // com.tencent.mm.network.q
    public final d Kv() {
        if(this.ecY == null) {
            this.ecY = this.HE();
            d v0 = this.ecY;
            v0.spH = com.tencent.mm.compatible.e.q.zg();
            v0.spG = com.tencent.mm.protocal.d.dOM;
            v0.spF = com.tencent.mm.protocal.d.spa;
            v0.fn(a.spv.HC());
        }

        return this.ecY;
    }
}
```
一. com.tencent.mm.compatible.e.q.zg()方法
```java
public static String zg() {
	return q.br(false);
}
```
* q.br(false)方法
```java
public static String br(boolean arg6) {
	String v0;
	if((arg6) || q.dyp == null) {
		StringBuilder v1 = new StringBuilder();
		v1.append(Settings.Secure.getString(ae.getContext().getContentResolver(), "android_id"));
		**** l.yp()读取CompatibleInfo.cfg文件
		v0 = (String)l.yP().get(256);
		if(arg6 || v0==null) {
			****A+手机IMEI前15位
			v0 = q.zk();
			l.yP().set(256, v0);
		}
		v1.append(v0);
		String v0_1 = (String)l.yP().get(259);
		if(v0_1 == null) {
			****n.yW()
			v0_1 = Build.MANUFACTURER + Build.MODEL + n.yW();
			l.yP().set(259, v0_1);
			y.d("MicroMsg.DeviceInfo", "getHardWareId " + v0_1);
		}
		else {
			y.d("MicroMsg.DeviceInfo", "getHardWareId from file " + v0_1);
		}

		v1.append(v0_1);
		String v0_2 = v1.toString();
		***g.o()
		q.dyp = "A" + g.o(v0_2.getBytes()).substring(0, 15);
		y.w("MicroMsg.DeviceInfo", "guid:%s, dev=%s", new Object[]{q.dyp, v0_2});
	}

	return q.dyp;
}
```
0.com.tencent.mm.compatible.e.l.yP()读取CompatibleInfo.cfg文件。文件内容为HashMap数据
```java
public static l yP() {
	l v0_1;
	Class v1 = l.class;
	synchronized(v1) {
		if(l.dxL == null) {
			l.dxL = new l(e.dOP + "CompatibleInfo.cfg");
		}

		v0_1 = l.dxL;
	}

	return v0_1;
}
```
1. q.zk()方法取取MIEI拼接字符串
```java
private static String zk() {
	String v0_1;
	int v1 = 0;
	String v0 = bk.fP(ae.getContext());//手机IMEi
	if(v0 != null && v0.length() > 0) {
		v0_1 = "A" + v0 + "123456789ABCDEF".substring(0, 15);
	}else {
		Random v2 = new Random();
		v2.setSeed(System.currentTimeMillis());
		v0_1 = "A";
		while(v1 < 15) {
			v0_1 = v0_1 + ((char)(v2.nextInt(25) + 65));
			++v1;
		}
	}

	y.w("MicroMsg.DeviceInfo", "generated deviceId=" + v0_1);
	return v0_1;
}
```
2. n.yW()方法
	```java
	public static String yW() {
		if(n.dxO == null) {
			***n.za()读取CPU信息
			n.dxO = n.za();
		}

		return ": " + n.b(n.dxO, "Features") + ": " + n.b(n.dxO, "Processor") + ": " + n.b(n.dxO, "CPU architecture") + ": " + n.b(n.dxO, "Hardware") + ": " + n.b(n.dxO, "Serial");
	}
	```
	- n.za()方法读取CPU信息
	```java
	public static HashMap za() {
        BufferedReader v1 = null;
        HashMap v3 = new HashMap();
        try {
            v1 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/cpuinfo"), "UTF-8"));
            while(true) {
            label_14:
                String v0_2 = v1.readLine();
                if(v0_2 == null) {
                    break;
                }

                String[] v0_3 = v0_2.split(":", 2);
                if(v0_3 == null || v0_3.length < 2) {
                    goto label_14;
                }

                String v2 = v0_3[0].trim();
                String v0_4 = v0_3[1].trim();
                if(v3.get(v2) != null) {
                    goto label_14;
                }

                v3.put(v2, v0_4);
            }
        }
        catch(IOException v0_1) {
            try {
                y.printErrStackTrace("CpuFeatures", v0_1, "getCpu() failed.", new Object[0]);
            }
            catch(Throwable v0) {
                bk.b(v1);
                throw v0;
            }

            bk.b(v1);
            return v3;
        }
        catch(Throwable v0) {
            bk.b(v1);
            throw v0;
        }

        bk.b(v1);
        return v3;
    }
	```
	- n.za()方法取出CPU信息
	```java
	private static String b(Map arg1, String arg2) {
        return (String)arg1.get(arg2);
    }
	```
3.com.tencent.mm.a.g.o(byte[]) 取MD5
```java
public static final String o(byte[] arg9) {
	char[] v3 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	try {
		MessageDigest v0_1 = MessageDigest.getInstance("MD5");
		v0_1.update(arg9);
		byte[] v4 = v0_1.digest();
		char[] v6 = new char[v4.length * 2];
		int v0_2 = 0;
		int v2;
		for(v2 = 0; v0_2 < v4.length; v2 = v8 + 1) {
			byte v7 = v4[v0_2];
			int v8 = v2 + 1;
			v6[v2] = v3[v7 >>> 4 & 15];
			v6[v8] = v3[v7 & 15];
			++v0_2;
		}

		return new String(v6);
	}
	catch(Exception v0) {
		return null;
	}
}
```	

	
	

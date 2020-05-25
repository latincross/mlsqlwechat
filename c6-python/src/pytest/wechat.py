from pyjava.api.mlsql import PythonContext
import pyarrow as pa
import pandas as pd

df = pd.read_csv("user_visit")

conf = {}
input_data = [pa.RecordBatch.from_pandas(df)]
data_manager = PythonContext(1 ,input_data, conf)

############

def process(_data_manager):
  pv = 0
  uv = 0
  lastUrl = None
  lastUid = None
  output = False

  for item in _data_manager.fetch_once_as_rows():
    urlCol = item["url"]
    uidCol = item["uid"]

    if lastUrl is None:
        pv = 1
        uv = 1
        lastUrl = urlCol
        lastUid = uidCol
    elif lastUrl == urlCol:
        pv = pv + 1
        if lastUid != uidCol:
          uv = uv + 1
          lastUid = uidCol
    else:
        #lastUrl变了，则输出
        output = True
        yield {"url":lastUrl,"pv":pv,"uv":uv}

        #重置pv，uv，output
        pv = 1
        uv = 1
        lastUrl = urlCol
        lastUid = uidCol
        output = False
  #当只有一个url情况下，lastUrl为空表示_data_manager没有数据，不需要输出
  if not output and lastUrl is not None:
      yield {"url":lastUrl,"pv":pv ,"uv":uv}

items=process(data_manager)
data_manager.build_result(items, 1024)

###########

print(next(data_manager.output()))


#!/bin/python

# conventions listing
# - use ALL_CAPS for a symbol in the global namespace
#   - this is basically because i completely disregard python's module system and use my own, lol

import os
from contextlib import contextmanager
from pprint import pprint
import inspect


def Record(**kwargs):
  from types import SimpleNamespace
  result = SimpleNamespace()
  for k, v in kwargs.items():
    setattr(result, k, v)

  return result

@contextmanager
def TEMP(filename, content):
  """returns a temporary file with the contents given"""
  import os.path
  import subprocess

  if not os.path.isdir('tmp'):
    CMD("mkdir tmp", "could not make ./tmp/ folder")
  with open(f"./tmp/{filename}", "w+") as out:
    out.write(content)
  try:
    with open(f"./tmp/{filename}", "r") as temp:
      yield temp
  finally:
    temp.close()

def CALL(cmd):
  DEBUG = True

  from subprocess import check_output, STDOUT, CalledProcessError
  try:
    result = check_output(cmd.split(), stderr=STDOUT, timeout=5).decode('latin-1'), 0
  except CalledProcessError as e:
    result = e.output.decode('latin-1'), e.returncode

  if DEBUG:
    print(f"{os.getcwd().rsplit('/', 1)[1]} $ {cmd} -> {result[1]}")
  return result

def CMD(cmd, msg):
  import sys
  output, return_code = CALL(cmd)
  if 0 != return_code:
    print("ERROR:", msg)
    print("OUTPUT:")
    print(output)
    sys.exit(return_code)
  return output

# wd = working directory
@contextmanager
def pushd(wd, f):
  cache_cwd = os.getcwd()
  try:
    os.chdir(wd)
    yield os.getcwd()
  finally:
    os.chdir(cache_cwd)

class BB:
  proj = ".."
  cpp = f"{proj}/cornerstone-cpp"

  # pass <key> takes in language
  passtable = [Record(passname=x[0], input_path=x[1], output=x[2]) for x in [
    # passname  , input_path                   , output
    ('include'  , 'input/bb-modular'           , 'intermediate/bb-strliteral'),
    ('str'      , 'intermediate/bb-strliteral' , 'intermediate/bb-tall'),
    ('normalize', 'intermediate/bb-tall'       , 'intermediate/bb-untyped'),
    ('typeinfer', 'intermediate/bb-untyped'    , 'intermediate/bb0'),
  ]]

  @staticmethod
  def FMT(filename):
    return CMD(f"{BB.cpp}/bin/bbfmt {out.name}",
               "failed to format {out.name}")

  @staticmethod
  def input_for_pass(passname):
    for p in BB.passtable:
      if passname == p.passname:
        return p
    return None


class BBTest:
  test = f"{BB.proj}/backbone-test"

  @classmethod
  def __runner__(cls, testcase, cmd, passname):
    folder = BB.input_for_pass(passname).input_path
    result = CMD(f"{BB.cpp}/bin/runner {cls.test}/{folder}/{testcase}.bb --{cmd} {passname}",
                 "runner failed on input")
    filename = f'{testcase}-{cmd}-{passname}.texp'
    with TEMP(filename, result) as out:
      return CALL(f"{BB.cpp}/bin/bbfmt {out.name}")

  @classmethod
  def single(cls, test, passname):
    return cls.__runner__(test, 'single', passname)

  def until(cls, test, passname):
    return cls.__runner__(test, 'until', passname)

  def all(cls, test):
    return CALL(f"{cls.cpp}/bin/cornerstone {cls.bb1}/3-str/{test}.texp")

  @classmethod
  def run(cls, test):
    result, code = CALL(f"{cls.cpp}/bin/cornerstone {cls.bb1}/3-str/{test}.texp")
    if code != 0:
      exit(1)
    with cls.tempfile(result) as out:
      result, code = CALL(f"clang -xir -Wno-override-module -o {cls.bbt}/tmp/{test} {out.name}")
      if code != 0:
        return (result, code)
      else:
        return CALL(f"{cls.bbt}/tmp/{test}")


def main():
  print(BBTest.single(test="hello", passname="include"))

if __name__ == "__main__":
  main()
  # pass

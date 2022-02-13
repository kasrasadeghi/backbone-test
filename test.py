#!/bin/python

# conventions listing
# - use ALL_CAPS for a symbol in the global namespace
#   - this is basically because i completely disregard python's module system and use my own, lol

import os
from contextlib import contextmanager
from pprint import pprint
import inspect

DEBUG = False

def CALL(cmd):
  global DEBUG

  from subprocess import check_output, STDOUT, CalledProcessError
  try:
    result = check_output(cmd.split(), stderr=STDOUT, timeout=5).decode('latin-1'), 0
  except CalledProcessError as e:
    result = e.output.decode('latin-1'), e.returncode

  if DEBUG:
    print(f"{os.getcwd().rsplit('/', 1)[1]} $ {cmd} -> {result[1]}")
  return result

def CMD():
  pass

def TEMP(content):
  """returns a temporary file with the contents given"""
  with open(f"{cls.bbt}/tmp/output", "w+") as out:
    out.write(content)
  try:
    with open(f"{cls.bbt}/tmp/output", "r") as temp:
      yield temp
  finally:
    temp.close()

# wd = working directory
def pushd(wd, f):
  cache_cwd = os.getcwd()
  try:
    os.chdir(wd)
    yield os.getcwd()
  finally:
    os.chdir(cache_cwd)

class BB:
  proj = "/home/kasra/projects"
  cpp = f"{proj}/cornerstone-cpp"

  # pass <key> takes in language
  passtable = [NamedTuple(passname=x[0], input=x[1], output=x[2]) for x in [
    # passname  , input          , output
    ('typeinfer', 'bb-untyped'   , 'bb0'),
    ('normalize', 'bb-tall'      , 'bb-untyped'),
    ('str'      , 'bb-strliteral', 'bb-tall'),
    ('include'  , 'bb-modular'   , 'bb-strliteral')
  ]]

  @staticmethod
  def FMT(filename):
    with TEMP(filename) as out:
      return call(f"{BB.cpp}/bin/bbfmt {out.name}")

  @classmethod
  def input_for_pass(passname):
    for p in passtable:
      if passname == p.passname:
        return p
    return None


class BBTest:
  test = f"{BB.proj}/backbone-test"

  @classmethod
  def __runner__(cls, testcase, cmd, passname):
    folder = BB.input_for_pass(passname).input
    result, code = call(f"{BB.cpp}/bin/runner {cls.test}/{folder}/{test}.texp --{cmd} {passname}")
    if code != 0:
      exit(1)
    with cls.tempfile(result) as out:
      return call(f"{cls.cpp}/bin/bbfmt {out.name}")

  @classmethod
  def single(cls, test, passname):
    return cls.__runner__(test, 'single', passname)

  def until(cls, test, passname):
    return cls.__runner__(test, 'until', passname)

  def all(cls, test):
    return call(f"{cls.cpp}/bin/cornerstone {cls.bb1}/3-str/{test}.texp")

  @classmethod
  def run(cls, test):
    result, code = call(f"{cls.cpp}/bin/cornerstone {cls.bb1}/3-str/{test}.texp")
    if code != 0:
      exit(1)
    with cls.tempfile(result) as out:
      result, code = call(f"clang -xir -Wno-override-module -o {cls.bbt}/tmp/{test} {out.name}")
      if code != 0:
        return (result, code)
      else:
        return call(f"{cls.bbt}/tmp/{test}")


def main():
  with pushd(BB.cpp):
    print(BBTest.single(test="auto", passname="str"))

if __name__ == "__main__":
  # main()
  pass
